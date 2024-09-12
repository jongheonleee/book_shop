package com.fastcampus.ch4.dto.notice;

import java.util.Date;
import java.util.Objects;

public class NoticeCateDto {
    private Integer ntc_cate_id;
    private String ntc_cate_code;
    private String name;
    private String use_chk;
    private Date reg_date;
    private String reg_id;
    private Date up_date;
    private String up_id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoticeCateDto that = (NoticeCateDto) o;
        return Objects.equals(ntc_cate_id, that.ntc_cate_id) && Objects.equals(ntc_cate_code, that.ntc_cate_code) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ntc_cate_id, ntc_cate_code, name);
    }

    public NoticeCateDto() {
    }

    public NoticeCateDto(String ntc_cate_code, String name, String use_chk, String reg_id) {
        this.ntc_cate_code = ntc_cate_code;
        this.name = name;
        this.use_chk = use_chk;
        this.reg_id = reg_id;
    }

    public Integer getNtc_cate_id() {
        return ntc_cate_id;
    }

    public void setNtc_cate_id(Integer ntc_cate_id) {
        this.ntc_cate_id = ntc_cate_id;
    }

    public String getNtc_cate_code() {
        return ntc_cate_code;
    }

    public void setNtc_cate_code(String ntc_cate_code) {
        this.ntc_cate_code = ntc_cate_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUse_chk() {
        return use_chk;
    }

    public void setUse_chk(String use_chk) {
        this.use_chk = use_chk;
    }

    public Date getReg_date() {
        return reg_date;
    }

    public void setReg_date(Date reg_date) {
        this.reg_date = reg_date;
    }

    public String getReg_id() {
        return reg_id;
    }

    public void setReg_id(String reg_id) {
        this.reg_id = reg_id;
    }

    public Date getUp_date() {
        return up_date;
    }

    public void setUp_date(Date up_date) {
        this.up_date = up_date;
    }

    public String getUp_id() {
        return up_id;
    }

    public void setUp_id(String up_id) {
        this.up_id = up_id;
    }
}
