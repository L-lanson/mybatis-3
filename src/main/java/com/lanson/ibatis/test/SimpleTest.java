package com.lanson.ibatis.test;

import com.lanson.ibatis.bean.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class SimpleTest {
  @Test
  public void insert() throws IOException {
    String resource = "mybatis-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    SqlSession session = sqlSessionFactory.openSession();
    User user = new User("1", "lanson", "深圳");
    session.insert("addUser", user);
    session.commit();
    session.close();
  }

  @Test
  public void testXPathParser() throws IOException {
    String resource = "mybatis-config.xml";
    XPathParser parser = new XPathParser(Resources.getResourceAsStream(resource));
    XNode xNode = parser.evalNode("/configuration/settings/setting");
    System.out.println(xNode.getStringAttribute("value"));
  }
}
