package com.fastcampus.ch4.dto.faq;

import java.util.Date;
import java.util.Objects;

public class FaqDto {
    private Integer faq_seq;
    private String faq_catg_code;
    private String title;
    private String cont;
    private String dsply_chk;
    private int view_cnt;
    private Date reg_date;
    private String reg_id;
    private Date up_date;
    private String up_id;
    private String catg_name;

    public FaqDto() {}

    public FaqDto(String faq_catg_code, String title, String cont, String dsply_chk, String reg_id) {
        this.faq_catg_code = faq_catg_code;
        this.title = title;
        this.cont = cont;
        this.dsply_chk = dsply_chk;
        this.reg_id = reg_id;
        this.up_id = reg_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FaqDto faqDto = (FaqDto) o;
        return Objects.equals(faq_seq, faqDto.faq_seq) && Objects.equals(faq_catg_code, faqDto.faq_catg_code) && Objects.equals(title, faqDto.title) && Objects.equals(cont, faqDto.cont) && Objects.equals(dsply_chk, faqDto.dsply_chk) && Objects.equals(reg_date, faqDto.reg_date) && Objects.equals(reg_id, faqDto.reg_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(faq_seq, faq_catg_code, title, cont, dsply_chk, reg_date, reg_id);
    }

    public Integer getFaq_seq() {
        return faq_seq;
    }

    public void setFaq_seq(Integer faq_seq) {
        this.faq_seq = faq_seq;
    }

    public String getFaq_catg_code() {
        return faq_catg_code;
    }

    public void setFaq_catg_code(String faq_catg_code) {
        this.faq_catg_code = faq_catg_code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public String getDsply_chk() {
        return dsply_chk;
    }

    public void setDsply_chk(String dsply_chk) {
        this.dsply_chk = dsply_chk;
    }

    public int getView_cnt() {
        return view_cnt;
    }

    public void setView_cnt(int view_cnt) {
        this.view_cnt = view_cnt;
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

    public String getCatg_name() {
        return catg_name;
    }

    public void setCatg_name(String catg_name) {
        this.catg_name = catg_name;
    }

    @Override
    public String toString() {
        return "FaqDto{" +
                "faq_seq=" + faq_seq +
                ", faq_catg_code=" + faq_catg_code +
                ", title='" + title + '\'' +
                ", cont='" + cont + '\'' +
                ", dsply_chk=" + dsply_chk +
                ", view_cnt=" + view_cnt +
                ", reg_date=" + reg_date +
                ", reg_id='" + reg_id + '\'' +
                ", up_date=" + up_date +
                ", up_id='" + up_id + '\'' +
                '}';
    }
}
