package com.fastcampus.ch4.dao.qa;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class QaCategoryDaoImpTest {

    @Autowired
    private QaCategoryDaoImp dao;

    @Before
    public void 초기화() {
        assertTrue(dao != null);
    }


}