package com.lanson.ibatis.test;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.decorators.BlockingCache;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.junit.Test;

public class CacheTest {
  @Test
  public void testBlockingCache(){
    Cache cache = new BlockingCache(new PerpetualCache("testBlockingCache"));
    if (cache.getObject("a") == null){
      cache.putObject("a", "b");
    }
    System.out.println(cache.getObject("a"));
  }
}
