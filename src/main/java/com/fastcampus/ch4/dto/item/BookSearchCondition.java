package com.fastcampus.ch4.dto.item;

import org.springframework.web.util.UriComponentsBuilder;

public class BookSearchCondition {
    private Integer page = 1;
    private Integer pageSize = 10;
    private String order_criteria = "book_reg_date";
    private String order_direction = "DESC";
//    private Integer offset = 0;
    private String keyword = "";
    private String option = "";

    public BookSearchCondition() {}

    public BookSearchCondition(Integer page) {
        this.page = page;
    }

    public BookSearchCondition(Integer page, Integer pageSize, String order_criteria, String order_direction, String keyword, String option) {
        this.page = page;
        this.pageSize = pageSize;
        this.order_criteria = order_criteria;
        this.order_direction = order_direction;
        this.keyword = keyword;
        this.option = option;
    }

    public String getQueryString(Integer page) {
        return UriComponentsBuilder.newInstance()
                .queryParam("page", page)
                .queryParam("pageSize", pageSize)
                .queryParam("order_criteria", order_criteria)
                .queryParam("order_direction", order_direction)
                .queryParam("option", option)
                .queryParam("keyword", keyword)
                .build().toString();
    }

    public String getQueryString() {
        // ?page=1&pageSize=10&order_criteria="book_reg_date"&order_direction="DESC"&keyword="title"&option="T"
        return getQueryString(page);
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        return (page-1)*pageSize;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getOrder_criteria() {
        return order_criteria;
    }

    public void setOrder_criteria(String order_criteria) {
        this.order_criteria = order_criteria;
    }

    public String getOrder_direction() {
        return order_direction;
    }

    public void setOrder_direction(String order_direction) {
        this.order_direction = order_direction;
    }

    @Override
    public String toString() {
        return "BookSearchCondition{" +
                "page=" + page +
                ", pageSize=" + pageSize +
                ", order_criteria='" + order_criteria + '\'' +
                ", order_direction='" + order_direction + '\'' +
                ", offset=" + getOffset() +
                ", keyword='" + keyword + '\'' +
                ", option='" + option + '\'' +
                '}';
    }
}
