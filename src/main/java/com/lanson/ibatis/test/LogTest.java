package com.lanson.ibatis.test;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.junit.Test;

public class LogTest {
  @Test
  public void testLogFactory(){
    LogFactory.useStdOutLogging();
    Log log = LogFactory.getLog(LogTest.class);
    System.out.println(log);
    log.debug("LOG测试debug");
    log.error("LOG测试error");
  }
}
