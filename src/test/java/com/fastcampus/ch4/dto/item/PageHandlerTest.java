package com.fastcampus.ch4.dto.item;

import org.junit.Test;

import static org.junit.Assert.*;

public class PageHandlerTest {
    @Test
    public void test() {
        // totalCnt = 250, page = 1
        PageHandler ph = new PageHandler(250, 1);
        ph.print();
        System.out.println(ph);
        assertTrue(ph.getTotalPage() == 50);
        assertTrue(ph.getBeginPage() == 1);
        assertTrue(ph.getEndPage() == 10);
        assertTrue(ph.isShowPrev() == false);
        assertTrue(ph.isShowNext() == true);
    }

    @Test
    public void test2() {
        //totalCnt = 250, page = 11
        PageHandler ph = new PageHandler(250, 11);
        ph.print();
        System.out.println(ph);
        assertTrue(ph.getTotalPage() == 50);
        assertTrue(ph.getBeginPage() == 11);
        assertTrue(ph.getEndPage() == 20);
        assertTrue(ph.isShowPrev() == true);
        assertTrue(ph.isShowNext() == true);
    }

    @Test
    public void test3() {
        //totalCnt = 255, page = 25
        PageHandler ph = new PageHandler(255, 25);
        ph.print();
        System.out.println(ph);
        assertTrue(ph.getTotalPage() == 51);
        assertTrue(ph.getBeginPage() == 21);
        assertTrue(ph.getEndPage() == 30);
        assertTrue(ph.isShowPrev() == true);
        assertTrue(ph.isShowNext() == true);
    }

    @Test
    public void test4() {
        //totalCnt = 250, page = 20
        PageHandler ph = new PageHandler(250, 20);
        ph.print();
        System.out.println(ph);
        assertTrue(ph.getTotalPage() == 50);
        assertTrue(ph.getBeginPage() == 11);
        assertTrue(ph.getEndPage() == 20);
        assertTrue(ph.isShowPrev() == true);
        assertTrue(ph.isShowNext() == true);
    }

    @Test
    public void test5() {
        //totalCnt = 250, page = 20
        PageHandler ph = new PageHandler(250, 50);
        ph.print();
        System.out.println(ph);
        assertTrue(ph.getTotalPage() == 50);
        assertTrue(ph.getBeginPage() == 41);
        assertTrue(ph.getEndPage() == 50);
        assertTrue(ph.isShowPrev() == true);
        assertTrue(ph.isShowNext() == false);
    }
}