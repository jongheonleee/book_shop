package com.fastcampus.ch4.dto.qa;

import java.util.Objects;

public class ReplyDto {
    private int qa_num;
    private String writer;
    private String content;
    private String creted_at;
    private String comt;
    private String reg_date;
    private String reg_id;
    private String up_date;
    private String up_id;

    public int getQa_num() {
        return qa_num;
    }

    public void setQa_num(int qa_num) {
        this.qa_num = qa_num;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreted_at() {
        return creted_at;
    }

    public void setCreted_at(String creted_at) {
        this.creted_at = creted_at;
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
        return "ReplyDto{" +
                "qa_num=" + qa_num +
                ", writer='" + writer + '\'' +
                ", content='" + content + '\'' +
                ", creted_at='" + creted_at + '\'' +
                ", comt='" + comt + '\'' +
                ", reg_date='" + reg_date + '\'' +
                ", reg_id='" + reg_id + '\'' +
                ", up_date='" + up_date + '\'' +
                ", up_id='" + up_id + '\'' +
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
        ReplyDto dto = (ReplyDto) o;
        return qa_num == dto.qa_num && Objects.equals(writer, dto.writer)
                && Objects.equals(content, dto.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(qa_num, writer, content);
    }
}
