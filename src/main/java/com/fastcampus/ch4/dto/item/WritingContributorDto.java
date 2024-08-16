package com.fastcampus.ch4.dto.item;

import java.util.Date;
import java.util.Objects;

public class WritingContributorDto {
    private String cb_num;//기여자 번호
    private String name;//이름
    private String job1;//직업1
    private String job2;//직업2
    private String cont_desc;//설명
    private char wr_chk;//작가여부
    private Date reg_date;//최초등록일시
    private String reg_id;//최초등록자식별번호
    private Date up_date;//최초수정일시
    private String up_id;//최종수정자식별번호

    public WritingContributorDto() {}

    public WritingContributorDto(String cb_num, String name) {
        this.cb_num = cb_num;
        this.name = name;
    }

    public WritingContributorDto(String cb_num, String name, String job1, String job2, String cont_desc, char wr_chk, Date reg_date, String reg_id, Date up_date, String up_id) {
        this.cb_num = cb_num;
        this.name = name;
        this.job1 = job1;
        this.job2 = job2;
        this.cont_desc = cont_desc;
        this.wr_chk = wr_chk;
        this.reg_date = reg_date;
        this.reg_id = reg_id;
        this.up_date = up_date;
        this.up_id = up_id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        WritingContributorDto that = (WritingContributorDto) object;
        return Objects.equals(cb_num, that.cb_num);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cb_num);
    }

    public String getCb_num() {
        return cb_num;
    }

    public void setCb_num(String cb_num) {
        this.cb_num = cb_num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob1() {
        return job1;
    }

    public void setJob1(String job1) {
        this.job1 = job1;
    }

    public String getJob2() {
        return job2;
    }

    public void setJob2(String job2) {
        this.job2 = job2;
    }

    public String getConst_desc() {
        return cont_desc;
    }

    public void setConst_desc(String cont_desc) {
        this.cont_desc = cont_desc;
    }

    public char getWr_chk() {
        return wr_chk;
    }

    public void setWr_chk(char wr_chk) {
        this.wr_chk = wr_chk;
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
        return "book_contributor{" +
                "cb_num='" + cb_num + '\'' +
                ", name='" + name + '\'' +
                ", job1='" + job1 + '\'' +
                ", job2='" + job2 + '\'' +
                ", cont_desc='" + cont_desc + '\'' +
                ", wr_chk=" + wr_chk +
                ", reg_date=" + reg_date +
                ", reg_id='" + reg_id + '\'' +
                ", up_date=" + up_date +
                ", up_id='" + up_id + '\'' +
                '}';
    }
}
