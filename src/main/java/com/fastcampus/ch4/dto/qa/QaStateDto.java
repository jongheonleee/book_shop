package com.fastcampus.ch4.dto.qa;

import java.util.Objects;

public class QaStateDto {

    private int qa_stat_seq;
    private String qa_stat_code;
    private String name;
    private String reg_date;
    private String reg_id;
    private String up_date;
    private String up_id;
    private int qa_num;
    private String appl_begin;
    private String appl_end;

    public QaStateDto() {}

    public QaStateDto(String name, int qa_num, String qa_stat_code) {
        this.name = name;
        this.qa_num = qa_num;
        this.qa_stat_code = qa_stat_code;
    }

    public int getQa_stat_seq() {
        return qa_stat_seq;
    }

    public void setQa_stat_seq(int qa_stat_seq) {
        this.qa_stat_seq = qa_stat_seq;
    }

    public String getQa_stat_code() {
        return qa_stat_code;
    }

    public void setQa_stat_code(String qa_stat_code) {
        this.qa_stat_code = qa_stat_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getQa_num() {
        return qa_num;
    }

    public void setQa_num(int qa_num) {
        this.qa_num = qa_num;
    }

    public String getAppl_begin() {
        return appl_begin;
    }

    public void setAppl_begin(String appl_begin) {
        this.appl_begin = appl_begin;
    }

    public String getAppl_end() {
        return appl_end;
    }

    public void setAppl_end(String appl_end) {
        this.appl_end = appl_end;
    }

    @Override
    public String toString() {
        return "QaStateDto{" +
                "qa_stat_seq=" + qa_stat_seq +
                ", qa_stat_code='" + qa_stat_code + '\'' +
                ", name='" + name + '\'' +
                ", reg_date='" + reg_date + '\'' +
                ", reg_id='" + reg_id + '\'' +
                ", up_date='" + up_date + '\'' +
                ", up_id='" + up_id + '\'' +
                ", qa_num=" + qa_num +
                ", appl_begin='" + appl_begin + '\'' +
                ", appl_end='" + appl_end + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QaStateDto that = (QaStateDto) o;
        return qa_stat_seq == that.qa_stat_seq && qa_num == that.qa_num && Objects.equals(
                qa_stat_code, that.qa_stat_code) && Objects.equals(name, that.name)
                && Objects.equals(appl_begin, that.appl_begin) && Objects.equals(
                appl_end, that.appl_end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(qa_stat_seq, qa_stat_code, name, qa_num, appl_begin, appl_end);
    }
}
