package com.fastcampus.ch4.dto.item;

import org.junit.Test;

import static org.junit.Assert.*;

public class PageHandlerTest {
    @Test
    public void test() {
        BookSearchCondition sc = new BookSearchCondition(1);
        // totalCnt = 250, page = 1
        PageHandler ph = new PageHandler(250, sc);
        ph.print();
        System.out.println(ph);
        assertTrue(ph.getTotalPage() == 25);
        assertTrue(ph.getBeginPage() == 1);
        assertTrue(ph.getEndPage() == 10);
        assertTrue(ph.isShowPrev() == false);
        assertTrue(ph.isShowNext() == true);
    }

    @Test
    public void test2() {
        BookSearchCondition sc = new BookSearchCondition(11);
        //totalCnt = 250, page = 11
        PageHandler ph = new PageHandler(250, sc);
        ph.print();
        System.out.println(ph);
        assertTrue(ph.getTotalPage() == 25);
        assertTrue(ph.getBeginPage() == 11);
        assertTrue(ph.getEndPage() == 20);
        assertTrue(ph.isShowPrev() == true);
        assertTrue(ph.isShowNext() == true);
    }

    @Test
    public void test3() {
        BookSearchCondition sc = new BookSearchCondition(25);
        //totalCnt = 255, page = 25
        PageHandler ph = new PageHandler(255, sc);
        ph.print();
        System.out.println(ph);
        assertTrue(ph.getTotalPage() == 26);
        assertTrue(ph.getBeginPage() == 21);
        assertTrue(ph.getEndPage() == 26);
        assertTrue(ph.isShowPrev() == true);
        assertTrue(ph.isShowNext() == false);
    }

    @Test
    public void test4() {
        BookSearchCondition sc = new BookSearchCondition(20);
        //totalCnt = 250, page = 20
        PageHandler ph = new PageHandler(250, sc);
        ph.print();
        System.out.println(ph);
        assertTrue(ph.getTotalPage() == 25);
        assertTrue(ph.getBeginPage() == 11);
        assertTrue(ph.getEndPage() == 20);
        assertTrue(ph.isShowPrev() == true);
        assertTrue(ph.isShowNext() == true);
    }
}