package com.fastcampus.ch4.dto.coupon;

public class CouponCategoryDto {

    private String cate_code;
    private String name;
    private String comt;
    private String top_cate_code;

    public String getCate_code() {
        return cate_code;
    }

    public void setCate_code(String cate_code) {
        this.cate_code = cate_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComt() {
        return comt;
    }

    public void setComt(String comt) {
        this.comt = comt;
    }

    public String getTop_cate_code() {
        return top_cate_code;
    }

    public void setTop_cate_code(String top_cate_code) {
        this.top_cate_code = top_cate_code;
    }

    @Override
    public String toString() {
        return "CouponCategoryDto{" +
                "cate_code='" + cate_code + '\'' +
                ", name='" + name + '\'' +
                ", comt='" + comt + '\'' +
                ", top_cate_code='" + top_cate_code + '\'' +
                '}';
    }
}
