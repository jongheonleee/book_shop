package com.fastcampus.ch4.dto.order.temp;

import java.util.Date;

public class TempBookDto {
    String isbn;
    Integer md_stat;
    String book_title;

    int papr_pric = 0;
    int e_pric = 0;
    int papr_point = 0;
    int e_point = 0;

    Date reg_date;
    String reg_id;
    Date up_date;
    String up_id;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getMd_stat() {
        return md_stat;
    }

    public void setMd_stat(Integer md_stat) {
        this.md_stat = md_stat;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public int getPapr_pric() {
        return papr_pric;
    }

    public void setPapr_pric(int papr_pric) {
        this.papr_pric = papr_pric;
    }

    public int getE_pric() {
        return e_pric;
    }

    public void setE_pric(int e_pric) {
        this.e_pric = e_pric;
    }

    public int getPapr_point() {
        return papr_point;
    }

    public void setPapr_point(int papr_point) {
        this.papr_point = papr_point;
    }

    public int getE_point() {
        return e_point;
    }

    public void setE_point(int e_point) {
        this.e_point = e_point;
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
