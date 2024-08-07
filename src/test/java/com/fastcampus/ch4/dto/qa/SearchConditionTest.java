package com.fastcampus.ch4.dto.qa;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class SearchConditionTest {

    private SearchCondition sc;

    @Before
    public void 초기화() {
    }

    @Test
    public void 안녕() {
        System.out.println("안녕");
    }
}