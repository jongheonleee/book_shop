package com.fastcampus.ch4.dao.qa;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QaCategoryDaoImp {

    @Autowired
    private SqlSession session;

    private static final String namespace = "com.fastcampus.ch4.QaCategoryMapper.";

    /**
     *  1차 기능 요구 사항 정리
     *
     **/


}
