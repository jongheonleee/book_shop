package com.fastcampus.ch4.dto.qa;

import org.springframework.web.util.UriComponentsBuilder;

public class PageHandler {

    private SearchCondition sc;
    private int totalCnt;
    private int totalPage;
    private int beginPage;
    private int endPage;
    private boolean prev;
    private boolean next;

    public PageHandler(int page, int totalCnt) {
        this(totalCnt, new SearchCondition(page, 10));
    }


    public PageHandler(int totalCnt, SearchCondition sc) {
        this.totalCnt = totalCnt;
        this.sc = sc;
        calculate();
    }

    private void calculate() {
        totalPage = (totalCnt / sc.getPageSize()) + (totalCnt % sc.getPageSize() == 0 ? 0 : 1);
        beginPage = ((sc.getPage()-1) / sc.getPageSize()) * sc.getPageSize() + 1;
        endPage = (beginPage - 1) + sc.getPageSize();
        endPage = endPage > totalPage ? totalPage : endPage;
        prev = beginPage == sc.getPage() ? false : true;
        next = endPage == totalPage ? false : true;
    }


    public int getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(int totalCnt) {
        this.totalCnt = totalCnt;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getBeginPage() {
        return beginPage;
    }

    public void setBeginPage(int beginPage) {
        this.beginPage = beginPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public boolean isPrev() {
        return prev;
    }

    public void setPrev(boolean prev) {
        this.prev = prev;
    }

    public boolean isNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public int getPage() {
        return sc.getPage();
    }

    public int getPageSize() {
        return sc.getPageSize();
    }

    public SearchCondition getSc() {
        return sc;
    }

    public void setSc(SearchCondition sc) {
        this.sc = sc;
    }

    public String getQuery() {
        return UriComponentsBuilder.newInstance()
                .queryParam("page", sc.getPage())
                .queryParam("pageSize", sc.getPageSize())
                .queryParam("option", sc.getOption())
                .queryParam("titleKeyword", sc.getTitleKeyword())
                .queryParam("period", sc.getPeriod())
                .build()
                .toString();
    }

    public void show() {
        // <
        if (prev) {
            System.out.print("<");
        }

        // 번호
        for (int i=beginPage; i<=endPage; i++) {
            System.out.print(" " + i + " ");
        }

        // >
        if (next) {
            System.out.print(">");
        }
    }

    @Override
    public String toString() {
        return "PageHandler{" +
                "sc=" + sc +
                ", totalCnt=" + totalCnt +
                ", totalPage=" + totalPage +
                ", beginPage=" + beginPage +
                ", endPage=" + endPage +
                ", prev=" + prev +
                ", next=" + next +
                '}';
    }
}
