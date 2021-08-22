package com.lanson.ibatis.test;

import com.lanson.ibatis.bean.User;
import org.apache.ibatis.reflection.*;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.BeanWrapper;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.junit.Test;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * MetaObject与ObjectWrapper相辅相成，ObjectWrapper为MetaObject提供PropertyTokenizer分词功能，MetaObject为ObjectWrapper提供对象操作功能
 * MetaClass依赖Reflector操作类，Reflector依赖TypeParameterResolver进行泛型类型的解析，Reflector的typeToClass()方法可获取泛型的原始类型
 */
public class ReflectionTest {

  /**
   * 创建对象实例的工厂类
   */
  @Test
  public void testObjectFactory(){
    String[] strings = {"a", "b", "c"};
    List<String> stringList = Arrays.asList(strings);

    ObjectFactory objectFactory = new DefaultObjectFactory();
    List<String> list = objectFactory.create(List.class, Collections.singletonList(Collection.class), Collections.singletonList(stringList));
    System.out.println(list);
  }

  /**
   * 属性分词器
   */
  @Test
  public void testPropertyTokenizer(){
    PropertyTokenizer propertyTokenizer = new PropertyTokenizer("a[1].b.c");
    while (true) {
      System.out.println("属性名：" + propertyTokenizer.getName() + "，下标：" + propertyTokenizer.getIndex());
      if (propertyTokenizer.hasNext()){
        propertyTokenizer = propertyTokenizer.next();
      }else {
        break;
      }
    }
  }

  /**
   * 对象包装器
   */
  @Test
  public void testBeanWrapper(){
    User lanson = new User("1", "lanson", "深圳");
    User cts = new User("2", "cts", "深圳");
    ObjectFactory objectFactory = new DefaultObjectFactory();
    ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();
    ReflectorFactory reflectorFactory = new DefaultReflectorFactory();
    MetaObject metaObject = MetaObject.forObject(cts, objectFactory, objectWrapperFactory, reflectorFactory);
    BeanWrapper beanWrapper = new BeanWrapper(metaObject);
    System.out.println(beanWrapper.get(new PropertyTokenizer("name")));
    beanWrapper.set(new PropertyTokenizer("lover"), lanson);
    beanWrapper.set(new PropertyTokenizer("lover.lover"), cts);
    System.out.println(beanWrapper.get(new PropertyTokenizer("lover.lover.name")));
  }

  @Test
  public void testMetaClass(){
    MetaClass metaClass = MetaClass.forClass(User.class, new DefaultReflectorFactory());
    System.out.println(metaClass.findProperty("lover.lover"));
    System.out.println(metaClass.getSetterType("lover"));
  }

  @Test
  public void testReflector() throws NoSuchFieldException {
    A<B, C> a = new A0();
    Class<?> classA = a.getClass();
    System.out.println(classA);
    System.out.println(A.class.getTypeParameters()[0]);
    System.out.println(classA.getGenericSuperclass());
    System.out.println(classA.getGenericSuperclass() instanceof ParameterizedType);//false
    System.out.println(((ParameterizedType)classA.getGenericSuperclass()).getActualTypeArguments()[0]);

    A1<B, C> a1 = new A1<>();
    System.out.println(a1.getClass().getGenericSuperclass());
    System.out.println(((ParameterizedType)a1.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    System.out.println(((ParameterizedType)a1.getClass().getGenericSuperclass()).getRawType());

    Field array = A.class.getDeclaredField("array");
    Type type = array.getGenericType();
    System.out.println(type instanceof GenericArrayType);
    GenericArrayType genericArrayType = (GenericArrayType) type;
    System.out.println(genericArrayType.getGenericComponentType());

    Field list = A.class.getDeclaredField("list");
    Type listType = list.getGenericType();
    System.out.println(listType);
    GenericArrayType listGenericArrayType = (GenericArrayType) listType;
    System.out.println(listGenericArrayType.getGenericComponentType());
    System.out.println(((ParameterizedType)listGenericArrayType.getGenericComponentType()).getRawType());
    System.out.println(Array.newInstance((Class<?>) (((ParameterizedType) listGenericArrayType.getGenericComponentType()).getRawType()), 0).getClass());
  }

  @Test
  public void testTypeParameterResolver() throws NoSuchFieldException {
    Field field = A.class.getDeclaredField("list");
    //System.out.println(TypeParameterResolver.resolveFieldType(field, A1.class));
    System.out.println(TypeParameterResolver.resolveFieldType(field, A1.class));
  }
}

class A0 extends A<B, C>{ }
class A1<T, U> extends A<T, U>{
  private T b;
}
class A<T, U>{
  private T[] array;
  private List<U>[] list;
}
class B{ }
class C{ }
