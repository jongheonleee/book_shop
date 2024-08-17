package com.fastcampus.ch4.dto.item;

public class PageHandler {
//    private int page; // 현재 페이지
//    private int pageSize; // 한 페이지의 크기
//    private String option; // 검색 옵션
//    private String keyword; // 검색 키워드
//    private String order_direction; //정렬방향
//    private String order_criteria; //정렬기준
    private BookSearchCondition bsc;
    private int totalCnt; // 총 게시물 개수
    private int naviSize = 10; // 페이지 내비게이션의 크기
    private int totalPage; // 전체 페이지의 수
    private int beginPage; // 내비게이션의 첫번째 페이지
    private int endPage; // 내비게이션의 마지막 페이지
    private boolean showPrev; // 이전 페이지로 이동하는 링크를 보여줄 것인지의 여부
    private boolean showNext; // 다음 페이지로 이동하는 링크를 보여줄 것인지의 여부

    public PageHandler(int totalCnt, BookSearchCondition bsc) {
        this.totalCnt = totalCnt;
        this.bsc = bsc;

        doPaging(totalCnt, bsc);
    }

    public void doPaging(int totalCnt, BookSearchCondition bsc) {

//        totalPage = totalCnt / pageSize + (totalCnt % pageSize == 0 ? 0 : 1);
        totalPage = (int)Math.ceil((double)totalCnt / bsc.getPageSize());
        beginPage =  (bsc.getPage() - 1) / naviSize * naviSize + 1;
        endPage = Math.min(beginPage + naviSize - 1, totalPage);
        showPrev = beginPage != 1;
        showNext = endPage != totalPage;
    }


    public int getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(int totalCnt) {
        this.totalCnt = totalCnt;
    }

    public int getNaviSize() {
        return naviSize;
    }

    public void setNaviSize(int naviSize) {
        this.naviSize = naviSize;
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

    public boolean isShowPrev() {
        return showPrev;
    }

    public void setShowPrev(boolean showPrev) {
        this.showPrev = showPrev;
    }

    public boolean isShowNext() {
        return showNext;
    }

    public void setShowNext(boolean showNext) {
        this.showNext = showNext;
    }

    public BookSearchCondition getBsc() {
        return bsc;
    }

    public void setBsc(BookSearchCondition bsc) {
        this.bsc = bsc;
    }

    public void print() {
        System.out.println("totalPage=" + totalPage);
        System.out.println("page = " + bsc.getPage());
        System.out.print(showPrev ? "[PREV] " : "");
        for (int i = beginPage; i <= endPage; i++) {
            System.out.print(i + " ");
        }
        System.out.println(showNext ? "[NEXT]" : "");
    }

    @Override
    public String toString() {
        return "PageHandler{" +
                "bsc=" + bsc +
                ", totalCnt=" + totalCnt +
                ", naviSize=" + naviSize +
                ", totalPage=" + totalPage +
                ", beginPage=" + beginPage +
                ", endPage=" + endPage +
                ", showPrev=" + showPrev +
                ", showNext=" + showNext +
                '}';
    }
}
