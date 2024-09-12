package com.fastcampus.ch4.dto.item;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class BookDto {

    // 도서 테이블
    private String isbn; //국제표준 도서번호
    private String cate_num; //카테고리 번호
    private String pub_name; //출판사명
    private String title; //책 제목
    private String pub_date; //발행(출시)일
    private String sale_stat; //판매상태
    private int sale_vol; //판매량
    private int papr_pric; //종이책 정가
    private double e_pric; //ebook 정가
    private double papr_point = 5; //종이책_기본적립률
    private double e_point = 5; //eBook_기본적립률
    private int tot_page_num; //쪽수
    private int tot_book_num; //권수
    private String sale_com; //도서판매코멘트
    private String cont; //목차
    private double rating; //평점
    private String info; //도서 정보
    private String intro_award; //수상내역/미디어 추천
    private String rec; //추천사
    private String pub_review; //출판사서평
    private int pre_start_page; //미리보기시작쪽수
    private int pre_end_page; //미리보기마지막쪽수
    private String ebook_url; //eBook URL
    private Date book_reg_date; // 도서 등록일
    private String regi_id; //도서 등록자 번호
    private Date reg_date;//최초등록일시
    private String reg_id;//최초등록자식별번호
    private Date up_date;//최초수정일시
    private String up_id;//최종수정자식별번호

    // 도서_이미지 테이블 - 가로,세로 크기 등등 추가 정보가 필요하면 반환타입 BookImageDto 활용
    private String repre_img; //대표이미지URL
    // private List<String> rest_img_urls; //나머지 이미지URL

    // 도서_할인_이력 테이블
    private double papr_disc; //종이책_할인율
    private double e_disc; //ebook_할인율

    // 카테고리 테이블
    //private CategoryDto category;//도서카테고리
    private String whol_layr_name; //전체카테고리명

    // 집필_기여자 테이블
    private String wr_cb_num; // 저자번호
    private String trl_cb_num; // 번역가번호
    private String wr_name; // 저자 이름
    private String trl_name; // 번역가 이름
//    private List<WritingContributorDto> authors; // 저자리스트
//    private List<WritingContributorDto> translators; // 번역가리스트

    public BookDto() {}

    public BookDto(String isbn) {
        this.isbn = isbn;
    }

    public BookDto(String isbn, String cate_num, String pub_name, String title, String pub_date, String sale_stat, int sale_vol, int papr_pric, double e_pric, double papr_point, double e_point, int tot_page_num, int tot_book_num, String sale_com, String cont, double rating, String info, String intro_award, String rec, String pub_review, int pre_start_page, int pre_end_page, String ebook_url, Date book_reg_date, String regi_id, Date reg_date, String reg_id, Date up_date, String up_id, String repre_img, double papr_disc, double e_disc, String whol_layr_name, String wr_cb_num, String trl_cb_num, String wr_name, String trl_name) {
        this.isbn = isbn;
        this.cate_num = cate_num;
        this.pub_name = pub_name;
        this.title = title;
        this.pub_date = pub_date;
        this.sale_stat = sale_stat;
        this.sale_vol = sale_vol;
        this.papr_pric = papr_pric;
        this.e_pric = e_pric;
        this.papr_point = papr_point;
        this.e_point = e_point;
        this.tot_page_num = tot_page_num;
        this.tot_book_num = tot_book_num;
        this.sale_com = sale_com;
        this.cont = cont;
        this.rating = rating;
        this.info = info;
        this.intro_award = intro_award;
        this.rec = rec;
        this.pub_review = pub_review;
        this.pre_start_page = pre_start_page;
        this.pre_end_page = pre_end_page;
        this.ebook_url = ebook_url;
        this.book_reg_date = book_reg_date;
        this.regi_id = regi_id;
        this.reg_date = reg_date;
        this.reg_id = reg_id;
        this.up_date = up_date;
        this.up_id = up_id;
        this.repre_img = repre_img;
        this.papr_disc = papr_disc;
        this.e_disc = e_disc;
        this.whol_layr_name = whol_layr_name;
        this.wr_cb_num = wr_cb_num;
        this.trl_cb_num = trl_cb_num;
        this.wr_name = wr_name;
        this.trl_name = trl_name;
    }

    public BookDto(String number, String number1, String publisher46, String title1, String s, String 판매중, int i, int i1, int i2, int i3, int i4, int i5, int i6, String saleCom0, String cont0, double v, String info0, String introAward0, String rec0, String pubReview0, int i7, int i8, String url, Date date, String user50, Date date1, String reg20, Date date2, String up48) {
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        BookDto bookDto = (BookDto) object;
        return Objects.equals(isbn, bookDto.isbn);
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCate_num() {
        return cate_num;
    }

    public void setCate_num(String cate_num) {
        this.cate_num = cate_num;
    }

    public String getPub_name() {
        return pub_name;
    }

    public void setPub_name(String pub_name) {
        this.pub_name = pub_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPub_date() {
        return pub_date;
    }

    public void setPub_date(String pub_date) {
        this.pub_date = pub_date;
    }

    public String getSale_stat() {
        return sale_stat;
    }

    public void setSale_stat(String sale_stat) {
        this.sale_stat = sale_stat;
    }

    public int getSale_vol() {
        return sale_vol;
    }

    public void setSale_vol(int sale_vol) {
        this.sale_vol = sale_vol;
    }

    public int getPapr_pric() {
        return papr_pric;
    }

    public void setPapr_pric(int papr_pric) {
        this.papr_pric = papr_pric;
    }

    public double getE_pric() {
        return e_pric;
    }

    public void setE_pric(double e_pric) {
        this.e_pric = e_pric;
    }

    public double getPapr_point() {
        return papr_point;
    }

    public void setPapr_point(double papr_point) {
        this.papr_point = papr_point;
    }

    public double getE_point() {
        return e_point;
    }

    public void setE_point(double e_point) {
        this.e_point = e_point;
    }

    public int getTot_page_num() {
        return tot_page_num;
    }

    public void setTot_page_num(int tot_page_num) {
        this.tot_page_num = tot_page_num;
    }

    public int getTot_book_num() {
        return tot_book_num;
    }

    public void setTot_book_num(int tot_book_num) {
        this.tot_book_num = tot_book_num;
    }

    public String getSale_com() {
        return sale_com;
    }

    public void setSale_com(String sale_com) {
        this.sale_com = sale_com;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getIntro_award() {
        return intro_award;
    }

    public void setIntro_award(String intro_award) {
        this.intro_award = intro_award;
    }

    public String getRec() {
        return rec;
    }

    public void setRec(String rec) {
        this.rec = rec;
    }

    public String getPub_review() {
        return pub_review;
    }

    public void setPub_review(String pub_review) {
        this.pub_review = pub_review;
    }

    public int getPre_start_page() {
        return pre_start_page;
    }

    public void setPre_start_page(int pre_start_page) {
        this.pre_start_page = pre_start_page;
    }

    public int getPre_end_page() {
        return pre_end_page;
    }

    public void setPre_end_page(int pre_end_page) {
        this.pre_end_page = pre_end_page;
    }

    public String getEbook_url() {
        return ebook_url;
    }

    public void setEbook_url(String ebook_url) {
        this.ebook_url = ebook_url;
    }

    public Date getBook_reg_date() {
        return book_reg_date;
    }

    public void setBook_reg_date(Date book_reg_date) {
        this.book_reg_date = book_reg_date;
    }

    public String getRegi_id() {
        return regi_id;
    }

    public void setRegi_id(String regi_id) {
        this.regi_id = regi_id;
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

    public String getRepre_img() {
        return repre_img;
    }

    public void setRepre_img(String repre_img) {
        this.repre_img = repre_img;
    }

    public double getPapr_disc() {
        return papr_disc;
    }

    public void setPapr_disc(double papr_disc) {
        this.papr_disc = papr_disc;
    }

    public double getE_disc() {
        return e_disc;
    }

    public void setE_disc(double e_disc) {
        this.e_disc = e_disc;
    }

    public String getWhol_layr_name() {
        return whol_layr_name;
    }

    public void setWhol_layr_name(String whol_layr_name) {
        this.whol_layr_name = whol_layr_name;
    }

    public String getWr_cb_num() {
        return wr_cb_num;
    }

    public void setWr_cb_num(String wr_cb_num) {
        this.wr_cb_num = wr_cb_num;
    }

    public String getTrl_cb_num() {
        return trl_cb_num;
    }

    public void setTrl_cb_num(String trl_cb_num) {
        this.trl_cb_num = trl_cb_num;
    }

    public String getWr_name() {
        return wr_name;
    }

    public void setWr_name(String wr_name) {
        this.wr_name = wr_name;
    }

    public String getTrl_name() {
        return trl_name;
    }

    public void setTrl_name(String trl_name) {
        this.trl_name = trl_name;
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "isbn='" + isbn + '\'' +
                ", cate_num='" + cate_num + '\'' +
                ", pub_name='" + pub_name + '\'' +
                ", title='" + title + '\'' +
                ", pub_date='" + pub_date + '\'' +
                ", sale_stat='" + sale_stat + '\'' +
                ", sale_vol=" + sale_vol +
                ", papr_pric=" + papr_pric +
                ", e_pric=" + e_pric +
                ", papr_point=" + papr_point +
                ", e_point=" + e_point +
                ", tot_page_num=" + tot_page_num +
                ", tot_book_num=" + tot_book_num +
                ", sale_com='" + sale_com + '\'' +
                ", cont='" + cont + '\'' +
                ", rating=" + rating +
                ", info='" + info + '\'' +
                ", intro_award='" + intro_award + '\'' +
                ", rec='" + rec + '\'' +
                ", pub_review='" + pub_review + '\'' +
                ", pre_start_page=" + pre_start_page +
                ", pre_end_page=" + pre_end_page +
                ", ebook_url='" + ebook_url + '\'' +
                ", book_reg_date=" + book_reg_date +
                ", regi_id='" + regi_id + '\'' +
                ", reg_date=" + reg_date +
                ", reg_id='" + reg_id + '\'' +
                ", up_date=" + up_date +
                ", up_id='" + up_id + '\'' +
                ", repre_img='" + repre_img + '\'' +
                ", papr_disc=" + papr_disc +
                ", e_disc=" + e_disc +
                ", whol_layr_name='" + whol_layr_name + '\'' +
                ", wr_cb_num='" + wr_cb_num + '\'' +
                ", trl_cb_num='" + trl_cb_num + '\'' +
                ", wr_name='" + wr_name + '\'' +
                ", trl_name='" + trl_name + '\'' +
                '}';
    }
}
