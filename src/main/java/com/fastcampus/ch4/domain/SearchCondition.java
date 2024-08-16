package com.fastcampus.ch4.domain;

public class SearchCondition {
    String option;
    String keyword;

    public SearchCondition(String option, String keyword) {
        this.option = option;
        this.keyword = keyword;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
