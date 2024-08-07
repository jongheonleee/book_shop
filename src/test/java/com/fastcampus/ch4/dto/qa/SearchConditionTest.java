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



    @Test
    public void test1() {
        SearchCondition sc = new SearchCondition(1, 10, "title", "안녕", 3);
        // ?page=1&pageSize=10&option=title&titleKeword=안녕&period=3
        assertTrue("?page=1&pageSize=10&option=title&titleKeyword=안녕&period=3".equals(sc.getQuery()));
        System.out.println("sc.getQuery() = " + sc.getQuery());
    }

}