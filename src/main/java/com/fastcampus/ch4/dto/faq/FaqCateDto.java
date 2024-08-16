package com.fastcampus.ch4.dto.faq;

import java.util.Date;
import java.util.Objects;

public class FaqCateDto {
    String cate_code;
    String name;
    char use_chk;
    String parent_code;
    Date reg_date;
    String reg_id;
    Date up_date;
    String up_id;

    public FaqCateDto() {}

    public FaqCateDto(String cate_code, String name, char use_chk, String reg_id) {
        this.cate_code = cate_code;
        this.name = name;
        this.use_chk = use_chk;
        this.parent_code = findParent_code(cate_code);
        this.reg_id = reg_id;
    }

    public String findParent_code(String cate_code) {
        String[] split = cate_code.trim().split("");
        int length = split.length;
        StringBuilder parent_code = new StringBuilder();
        for (int i = 0; i < length-2; i++) {
            parent_code.append(split[i]);
        }
        return parent_code.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FaqCateDto that = (FaqCateDto) o;
        return Objects.equals(cate_code, that.cate_code) && Objects.equals(name, that.name) && Objects.equals(parent_code, that.parent_code) && Objects.equals(reg_date, that.reg_date) && Objects.equals(reg_id, that.reg_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cate_code, name, parent_code, reg_date, reg_id);
    }

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

    public char getUse_chk() {
        return use_chk;
    }

    public void setUse_chk(char use_chk) {
        this.use_chk = use_chk;
    }

    public String getParent_code() {
        return parent_code;
    }

    public void setParent_code(String parent_code) {
        this.parent_code = parent_code;
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

    @Override
    public String toString() {
        return "FaqCateDto{" +
                "cate_code='" + cate_code + '\'' +
                ", name='" + name + '\'' +
                ", use_chk=" + use_chk +
                ", parent_code='" + parent_code + '\'' +
                ", reg_date=" + reg_date +
                ", reg_id='" + reg_id + '\'' +
                ", up_date=" + up_date +
                ", up_id='" + up_id + '\'' +
                '}';
    }
}
