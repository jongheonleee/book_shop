package com.fastcampus.ch4.dto.qa;

public class QaCategoryDto {
    private String qa_cate_num;
    private String name;
    private String comt;
    private String reg_date;
    private String reg_id;
    private String up_date;
    private String up_id;
    private String chk_use;

    public String getQa_cate_num() {
        return qa_cate_num;
    }

    public void setQa_cate_num(String qa_cate_num) {
        this.qa_cate_num = qa_cate_num;
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

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String getReg_id() {
        return reg_id;
    }

    public void setReg_id(String reg_id) {
        this.reg_id = reg_id;
    }

    public String getUp_date() {
        return up_date;
    }

    public void setUp_date(String up_date) {
        this.up_date = up_date;
    }

    public String getUp_id() {
        return up_id;
    }

    public void setUp_id(String up_id) {
        this.up_id = up_id;
    }

    public String getChk_use() {
        return chk_use;
    }

    public void setChk_use(String chk_use) {
        this.chk_use = chk_use;
    }

    @Override
    public String toString() {
        return "QaCategoryDto{" +
                "qa_cate_num='" + qa_cate_num + '\'' +
                ", name='" + name + '\'' +
                ", comt='" + comt + '\'' +
                ", reg_date='" + reg_date + '\'' +
                ", reg_id='" + reg_id + '\'' +
                ", up_date='" + up_date + '\'' +
                ", up_id='" + up_id + '\'' +
                ", chk_use='" + chk_use + '\'' +
                '}';
    }
}
