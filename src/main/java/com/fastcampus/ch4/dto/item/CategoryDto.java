package com.fastcampus.ch4.dto.item;

import java.util.Date;
import java.util.Objects;

public class CategoryDto {
    private String cate_num;
    private String name;
    private int lev;
    private char last_cate_chk;
    private String cur_layr_num;
    private String whol_layr_name;
    private Date reg_date;
    private String reg_id;
    private Date up_date;
    private String up_id;

    public CategoryDto() {}

    public CategoryDto(String cate_num, String name, int lev, char last_cate_chk, String cur_layr_num, String whol_layr_name, Date reg_date, String reg_id, Date up_date, String up_id) {
        this.cate_num = cate_num;
        this.name = name;
        this.lev = lev;
        this.last_cate_chk = last_cate_chk;
        this.cur_layr_num = cur_layr_num;
        this.whol_layr_name = whol_layr_name;
        this.reg_date = reg_date;
        this.reg_id = reg_id;
        this.up_date = up_date;
        this.up_id = up_id;
    }

    public CategoryDto(String cate_num, String name, int lev, char last_cate_chk, String cur_layr_num, String whol_layr_name) {
        this.cate_num = cate_num;
        this.name = name;
        this.lev = lev;
        this.last_cate_chk = last_cate_chk;
        this.cur_layr_num = cur_layr_num;
        this.whol_layr_name = whol_layr_name;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CategoryDto that = (CategoryDto) object;
        return lev == that.lev && last_cate_chk == that.last_cate_chk && Objects.equals(cate_num, that.cate_num) && Objects.equals(name, that.name) && Objects.equals(cur_layr_num, that.cur_layr_num) && Objects.equals(whol_layr_name, that.whol_layr_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cate_num, name, lev, last_cate_chk, cur_layr_num, whol_layr_name);
    }

    @Override
    public String
    toString() {
        return "CategoryDto{" +
                "cate_num='" + cate_num + '\'' +
                ", name='" + name + '\'' +
                ", lev=" + lev +
                ", last_cate_chk=" + last_cate_chk +
                ", cur_layr_num='" + cur_layr_num + '\'' +
                ", whol_layr_name='" + whol_layr_name + '\'' +
                ", reg_date=" + reg_date +
                ", reg_id='" + reg_id + '\'' +
                ", up_date=" + up_date +
                ", up_id='" + up_id + '\'' +
                '}';
    }

    public String getCate_num() {
        return cate_num;
    }

    public void setCate_num(String cate_num) {
        this.cate_num = cate_num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLev() {
        return lev;
    }
    public void setLev(int lev) {
        this.lev = lev;
    }

    public char getLast_cate_chk() {
        return last_cate_chk;
    }

    public void setLast_cate_chk(char last_cate_chk) {
        this.last_cate_chk = last_cate_chk;
    }

    public String getCur_layr_num() {
        return cur_layr_num;
    }

    public void setCur_layr_num(String cur_layr_num) {
        this.cur_layr_num = cur_layr_num;
    }

    public String getWhol_layr_name() {
        return whol_layr_name;
    }

    public void setWhol_layr_name(String whol_layr_name) {
        this.whol_layr_name = whol_layr_name;
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
