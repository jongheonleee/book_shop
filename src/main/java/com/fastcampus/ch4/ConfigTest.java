package com.fastcampus.ch4;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

  public class ConfigTest {

    public static void main(String[] args) throws Exception {
      // 스키마의 이름(springbasic)이 다른 경우 알맞게 변경해야 함

      ApplicationContext ac = new GenericXmlApplicationContext("file:src/main/webapp/WEB-INF/spring/root-context.xml");
      System.out.println("ac = " + ac);
    }
  }


