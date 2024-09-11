package com.fastcampus.ch4.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/*
이름 : GenerateTime
역할 : 시간을 반환하는 클래스
 */
public class GenerateTime {

    /*
    역할 : 최대 시간(9999-12-31-23:59:59) 반환 메서드
     */
    public static String getMaxTime() {
        return "9999-12-31-23:59:59";
    }

    /*
    역할 : 현재 시간 을 "yyyy-mm-dd hh:mm:ss" 형태로 반환하는 메서드
     */
    public static String getCurrentTime() {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentTimestamp);
    }

}
