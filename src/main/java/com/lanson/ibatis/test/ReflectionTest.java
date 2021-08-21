package com.lanson.ibatis.test;

import com.lanson.ibatis.bean.User;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.BeanWrapper;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
}
