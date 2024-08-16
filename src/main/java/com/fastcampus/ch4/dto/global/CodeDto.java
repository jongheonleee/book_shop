package com.fastcampus.ch4.dto.global;

public class CodeDto {

    private int code_id;
    private String cate_num;
    private String code;
    private String code_name;
    private int ord;
    private char chk_use;
    private String comt;
    private String reg_date;
    private String reg_id;
    private String up_date;
    private String up_id;

    public int getCode_id() {
        return code_id;
    }

    public void setCode_id(int code_id) {
        this.code_id = code_id;
    }

    public String getCate_num() {
        return cate_num;
    }

    public void setCate_num(String cate_num) {
        this.cate_num = cate_num;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode_name() {
        return code_name;
    }

    public void setCode_name(String code_name) {
        this.code_name = code_name;
    }

    public int getOrd() {
        return ord;
    }

    public void setOrd(int ord) {
        this.ord = ord;
    }

    public char getChk_use() {
        return chk_use;
    }

    public void setChk_use(char chk_use) {
        this.chk_use = chk_use;
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

    @Override
    public String toString() {
        return "CodeDto{" +
                "code_id=" + code_id +
                ", cate_num='" + cate_num + '\'' +
                ", code='" + code + '\'' +
                ", code_name='" + code_name + '\'' +
                ", ord=" + ord +
                ", chk_use=" + chk_use +
                ", comt='" + comt + '\'' +
                ", reg_date='" + reg_date + '\'' +
                ", reg_id='" + reg_id + '\'' +
                ", up_date='" + up_date + '\'' +
                ", up_id='" + up_id + '\'' +
                '}';
    }
}
