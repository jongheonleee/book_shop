package com.fastcampus.ch4.dto.qa;

import static org.junit.Assert.assertTrue;

import com.fastcampus.ch4.domain.qa.PageHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class PageHandlerTest {

    private PageHandler ph;


    @Test
    public void 테스트1() {
        ph = new PageHandler(1, 100);
        System.out.println("ph.getQuery() = " + ph.getQuery());
        assertTrue(ph.getQuery().equals("?page=1&pageSize=10&option&titleKeyword&period=0"));

        System.out.println(ph);
        // 1 2 ... 10
        ph.show();
    }

    @Test
    public void 테스트2() {
        ph = new PageHandler(12, 1000);
        System.out.println("ph.getQuery() = " + ph.getQuery());
        assertTrue(ph.getQuery().equals("?page=12&pageSize=10&option&titleKeyword&period=0"));

        // < 11 12 ... 20 >
        ph.show();

    }

    @Test
    public void 테스트3() {
        ph = new PageHandler(16, 256);
        assertTrue(ph.getQuery().equals("?page=16&pageSize=10&option&titleKeyword&period=0"));
        System.out.println(ph.getQuery());

        System.out.println(ph);
        // < 11 12 ... 20 >
        ph.show();
    }

    @Test
    public void 테스트4() {
        ph = new PageHandler(3, 90);
        assertTrue(ph.getQuery().equals("?page=3&pageSize=10&option&titleKeyword&period=0"));
        System.out.println(ph.getQuery());

        System.out.println(ph);
        // < 1 2 3... 9
        ph.show();
    }

    @Test
    public void 테스트5() {
        ph = new PageHandler(24, 500);
        assertTrue(ph.getQuery().equals("?page=24&pageSize=10&option&titleKeyword&period=0"));
        System.out.println(ph.getQuery());

        System.out.println(ph);
        // < 21 22 ... 30 >
        ph.show();
    }
}