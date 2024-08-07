package com.fastcampus.ch4.dto.qa;

public class QaDto {

    private int qa_num;
    private String user_id;
    private String qa_cate_num;
    private String admin;
    private String title;
    private String content;
    private String created_at;
    private String chk_repl;
    private String reg_repl_date;
    private String email;
    private String tele_num;
    private String phon_num;
    private String img1;
    private String img2;
    private String img3;
    private String comt;

    public int getQa_num() {
        return qa_num;
    }

    public void setQa_num(int qa_num) {
        this.qa_num = qa_num;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getQa_cate_num() {
        return qa_cate_num;
    }

    public void setQa_cate_num(String qa_cate_num) {
        this.qa_cate_num = qa_cate_num;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getChk_repl() {
        return chk_repl;
    }

    public void setChk_repl(String chk_repl) {
        this.chk_repl = chk_repl;
    }

    public String getReg_repl_date() {
        return reg_repl_date;
    }

    public void setReg_repl_date(String reg_repl_date) {
        this.reg_repl_date = reg_repl_date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTele_num() {
        return tele_num;
    }

    public void setTele_num(String tele_num) {
        this.tele_num = tele_num;
    }

    public String getPhon_num() {
        return phon_num;
    }

    public void setPhon_num(String phon_num) {
        this.phon_num = phon_num;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getComt() {
        return comt;
    }

    public void setComt(String comt) {
        this.comt = comt;
    }

    @Override
    public String toString() {
        return "QaDto{" +
                "qa_num='" + qa_num + '\'' +
                ", user_id='" + user_id + '\'' +
                ", qa_cate_num='" + qa_cate_num + '\'' +
                ", admin='" + admin + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", created_at='" + created_at + '\'' +
                ", chk_repl='" + chk_repl + '\'' +
                ", reg_repl_date='" + reg_repl_date + '\'' +
                ", email='" + email + '\'' +
                ", tele_num='" + tele_num + '\'' +
                ", phon_num='" + phon_num + '\'' +
                ", img1='" + img1 + '\'' +
                ", img2='" + img2 + '\'' +
                ", img3='" + img3 + '\'' +
                ", comt='" + comt + '\'' +
                '}';
    }
}
