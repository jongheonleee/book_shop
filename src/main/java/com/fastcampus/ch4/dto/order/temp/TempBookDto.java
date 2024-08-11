package com.fastcampus.ch4.dto.order.temp;

import java.util.Date;

public class TempBookDto {
    String isbn;
    Integer md_stat;
    String book_title;

    // 정가
    Integer papr_pric;
    Integer e_pric;
    // 적립
    Double papr_point; // 적립
    Double e_point;

    // 할인 - 임시로 넣어준 값
    Double papr_disc;
    Double e_disc;
    Integer paper_bene_pric;
    Integer e_bene_pric;


    // 시스템컬럼
//    Date reg_date;
//    String reg_id;
//    Date up_date;
//    String up_id;

    public TempBookDto(String isbn, Integer md_stat, String book_title, Integer papr_pric, Integer e_pric, Double papr_point, Double e_point, Double papr_disc, Double e_disc, Integer paper_bene_pric, Integer e_bene_pric) {
        this.isbn = isbn;
        this.md_stat = md_stat;
        this.book_title = book_title;
        this.papr_pric = papr_pric;
        this.e_pric = e_pric;
        this.papr_point = papr_point;
        this.e_point = e_point;
        this.papr_disc = papr_disc;
        this.e_disc = e_disc;
        this.paper_bene_pric = paper_bene_pric;
        this.e_bene_pric = e_bene_pric;
    }

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

    public Integer getPapr_pric() {
        return papr_pric;
    }

    public void setPapr_pric(Integer papr_pric) {
        this.papr_pric = papr_pric;
    }

    public Integer getE_pric() {
        return e_pric;
    }

    public void setE_pric(Integer e_pric) {
        this.e_pric = e_pric;
    }

    public Double getPapr_point() {
        return papr_point;
    }

    public void setPapr_point(Double papr_point) {
        this.papr_point = papr_point;
    }

    public Double getE_point() {
        return e_point;
    }

    public void setE_point(Double e_point) {
        this.e_point = e_point;
    }

    public Double getPapr_disc() {
        return papr_disc;
    }

    public void setPapr_disc(Double papr_disc) {
        this.papr_disc = papr_disc;
    }

    public Double getE_disc() {
        return e_disc;
    }

    public void setE_disc(Double e_disc) {
        this.e_disc = e_disc;
    }

    public Integer getPaper_bene_pric() {
        return paper_bene_pric;
    }

    public void setPaper_bene_pric(Integer paper_bene_pric) {
        this.paper_bene_pric = paper_bene_pric;
    }

    public Integer getE_bene_pric() {
        return e_bene_pric;
    }

    public void setE_bene_pric(Integer e_bene_pric) {
        this.e_bene_pric = e_bene_pric;
    }

}
