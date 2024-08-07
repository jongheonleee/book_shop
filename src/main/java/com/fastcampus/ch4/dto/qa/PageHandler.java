package com.fastcampus.ch4.dto.qa;

public class PageHandler {
    private final int pageSize  = 10;

    private int totalCnt;
    private int page;
    private int totalPage;
    private int beginPage;
    private int endPage;
    private boolean prev;
    private boolean next;

    public PageHandler(int page, int totalCnt) {
        this.page = page;
        this.totalCnt = totalCnt;
        calculate();
    }

    private void calculate() {
        totalPage = (totalCnt / pageSize) + (totalCnt % pageSize == 0 ? 0 : 1);
        beginPage = ((page-1) / pageSize) * pageSize + 1;
        endPage = (beginPage - 1) + pageSize;
        endPage = endPage > totalPage ? totalPage : endPage;
        prev = beginPage == page ? false : true;
        next = endPage == totalPage ? false : true;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(int totalCnt) {
        this.totalCnt = totalCnt;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
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

    public String getQuery() {
        return "?page=" + page + "&pageSize=" + pageSize;
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
                "pageSize=" + pageSize +
                ", totalCnt=" + totalCnt +
                ", page=" + page +
                ", totalPage=" + totalPage +
                ", beginPage=" + beginPage +
                ", endPage=" + endPage +
                ", prev=" + prev +
                ", next=" + next +
                '}';
    }
}
