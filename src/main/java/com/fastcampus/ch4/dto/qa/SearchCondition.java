package com.fastcampus.ch4.dto.qa;

import org.springframework.web.util.UriComponentsBuilder;

public class SearchCondition {

    private int page;
    private int pageSize;
    private String option;
    private String titleKeyword;
    private int period;

    public SearchCondition() {
    }

    public SearchCondition(int page, int pageSize, String option, String titleKeyword, int period) {
        this.page = page;
        this.pageSize = pageSize;
        this.option = option;
        this.titleKeyword = titleKeyword;
        this.period = period;
    }

    public String getQuery() {
        return UriComponentsBuilder.newInstance()
                .queryParam("page", page)
                .queryParam("pageSize", pageSize)
                .queryParam("option", option)
                .queryParam("titleKeyword", titleKeyword)
                .queryParam("period", period)
                .build()
                .toString();
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getTitleKeyword() {
        return titleKeyword;
    }

    public void setTitleKeyword(String titleKeyword) {
        this.titleKeyword = titleKeyword;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    @Override
    public String toString() {
        return "SearchCondition{" +
                "page=" + page +
                ", pageSize=" + pageSize +
                ", option='" + option + '\'' +
                ", titleKeyword='" + titleKeyword + '\'' +
                ", period=" + period +
                '}';
    }
}
