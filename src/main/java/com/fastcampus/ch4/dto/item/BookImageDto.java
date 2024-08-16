package com.fastcampus.ch4.dto.item;

import java.util.Date;
import java.util.Objects;

public class BookImageDto {

    private String isbn;
    private int img_seq; //이미지 시퀀스
    private String img_url; //이미지URL
    private int img_hrzt_size;//이미지_가로크기
    private int img_vrtc_size;//이미지_가로크기
    private String img_file_format;//이미지_파일형식
    private Date img_regi_day;//이미지_등록일
    private String img_desc;//이미지_설명
    private char main_img_chk;//대표이미지_여부
    private Date reg_date;//최초등록일시
    private String reg_id;//최초등록자식별번호
    private Date up_date;//최초수정일시
    private String up_id;//최종수정자식별번호

    public BookImageDto() {}

    public BookImageDto(String isbn) {
        this.isbn = isbn;
    }

    public BookImageDto(String isbn, int img_seq) {
        this.isbn = isbn;
        this.img_seq = img_seq;
    }

    public BookImageDto(String isbn, int img_seq, String img_url, int img_hrzt_size, int img_vrtc_size, String img_file_format, Date img_regi_day, String img_desc, char main_img_chk, Date reg_date, String reg_id, Date up_date, String up_id) {
        this.isbn = isbn;
        this.img_seq = img_seq;
        this.img_url = img_url;
        this.img_hrzt_size = img_hrzt_size;
        this.img_vrtc_size = img_vrtc_size;
        this.img_file_format = img_file_format;
        this.img_regi_day = img_regi_day;
        this.img_desc = img_desc;
        this.main_img_chk = main_img_chk;
        this.reg_date = reg_date;
        this.reg_id = reg_id;
        this.up_date = up_date;
        this.up_id = up_id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        BookImageDto that = (BookImageDto) object;
        return img_seq == that.img_seq && Objects.equals(isbn, that.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, img_seq);
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getImg_seq() {
        return img_seq;
    }

    public void setImg_seq(int img_seq) {
        this.img_seq = img_seq;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getImg_hrzt_size() {
        return img_hrzt_size;
    }

    public void setImg_hrzt_size(int img_hrzt_size) {
        this.img_hrzt_size = img_hrzt_size;
    }

    public int getImg_vrtc_size() {
        return img_vrtc_size;
    }

    public void setImg_vrtc_size(int img_vrtc_size) {
        this.img_vrtc_size = img_vrtc_size;
    }

    public String getImg_file_format() {
        return img_file_format;
    }

    public void setImg_file_format(String img_file_format) {
        this.img_file_format = img_file_format;
    }

    public Date getImg_regi_day() {
        return img_regi_day;
    }

    public void setImg_regi_day(Date img_regi_day) {
        this.img_regi_day = img_regi_day;
    }

    public String getImg_desc() {
        return img_desc;
    }

    public void setImg_desc(String img_desc) {
        this.img_desc = img_desc;
    }

    public char getMain_img_chk() {
        return main_img_chk;
    }

    public void setMain_img_chk(char main_img_chk) {
        this.main_img_chk = main_img_chk;
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
        return "BookImageDto{" +
                "isbn='" + isbn + '\'' +
                ", img_seq=" + img_seq +
                ", img_url='" + img_url + '\'' +
                ", img_hrzt_size=" + img_hrzt_size +
                ", img_vrtc_size=" + img_vrtc_size +
                ", img_file_format='" + img_file_format + '\'' +
                ", img_regi_day=" + img_regi_day +
                ", img_desc='" + img_desc + '\'' +
                ", main_img_chk=" + main_img_chk +
                ", reg_date=" + reg_date +
                ", reg_id='" + reg_id + '\'' +
                ", up_date=" + up_date +
                ", up_id='" + up_id + '\'' +
                '}';
    }
}
