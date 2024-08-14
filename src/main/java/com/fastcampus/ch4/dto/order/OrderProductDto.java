package com.fastcampus.ch4.dto.order;

import java.util.Date;
import java.util.Objects;

public class OrderProductDto {
    // PK
    private Integer ord_prod_seq; // 주문 상품 번호

    // FK
    private Integer ord_seq; // 주문 번호
    private String isbn; // 도서 pk
    private String prod_type_code; // 상품 유형 코드

    // NOT NULL
    private String ord_stat; // 주문 상태
    private String deli_stat; // 배송 상태
    private String pay_stat; // 결제 상태

    // NULL
    private Integer item_quan; //주문 상품 수량
    private Double point_perc; // 적립율
    private Integer point_pric; // 적립금
    private Integer basic_pric; // 상품 정가
    private Double bene_perc; // 상품 할인율
    private Integer bene_pric; // 상품 할인 가격
    private Integer sale_pric; // 상품 판매 가격
    private Integer ord_pric; // 주문 금액

    // 시스템 컬럼  - equals 제외
    private Date reg_date; // 최초 등록 일시
    private String reg_id; // 최초 등록 id
    private Date up_date; // 최근 수정 일시
    private String up_id; // 최근 수정 id

    /*
    객체를 생성할 때 어떻게 생성해줘야 하나?
    => 정적 팩토리 메서드로 생성해준다.
    근데? 기본적인 값의 셋팅은 어떻게 해주는 것이 좋을까?
    => Order, Book, bookType, itemQuantity 를 받아서 생성해줘야 한다.
    => bookType 에 따라서 값을 셋팅해서 보여주는 로직이 여기에 있는 것이 맞을까?


    => select 의 결과는 mybatis 에서 select 부분의 ResultType 을 설정해주는 것으로 가능하다.

    - DTO 를 Builder 로 하는 것은 비용대비 시간이 좋지 않다.

     */

    private OrderProductDto() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderProductDto)) return false;
        OrderProductDto that = (OrderProductDto) o;
        return Objects.equals(ord_seq, that.ord_seq) && Objects.equals(isbn, that.isbn) && Objects.equals(prod_type_code, that.prod_type_code) && Objects.equals(ord_stat, that.ord_stat) && Objects.equals(deli_stat, that.deli_stat) && Objects.equals(pay_stat, that.pay_stat) && Objects.equals(item_quan, that.item_quan) && Objects.equals(point_perc, that.point_perc) && Objects.equals(point_pric, that.point_pric) && Objects.equals(basic_pric, that.basic_pric) && Objects.equals(bene_perc, that.bene_perc) && Objects.equals(bene_pric, that.bene_pric) && Objects.equals(sale_pric, that.sale_pric) && Objects.equals(ord_pric, that.ord_pric) && Objects.equals(reg_id, that.reg_id) && Objects.equals(up_id, that.up_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ord_seq, isbn, prod_type_code, ord_stat, deli_stat, pay_stat, item_quan, point_perc, point_pric, basic_pric, bene_perc, bene_pric, sale_pric, ord_pric, reg_id, up_id);
    }

    @Override
    public String toString() {
        return "OrderProductDto{" +
                "ord_prod_seq=" + ord_prod_seq +
                ", ord_seq=" + ord_seq +
                ", isbn='" + isbn + '\'' +
                ", prod_type_code='" + prod_type_code + '\'' +
                ", ord_stat='" + ord_stat + '\'' +
                ", deli_stat='" + deli_stat + '\'' +
                ", pay_stat='" + pay_stat + '\'' +
                ", item_quan=" + item_quan +
                ", point_perc=" + point_perc +
                ", point_pric=" + point_pric +
                ", basic_pric=" + basic_pric +
                ", bene_perc=" + bene_perc +
                ", bene_pric=" + bene_pric +
                ", sale_pric=" + sale_pric +
                ", ord_pric=" + ord_pric +
                ", reg_date=" + reg_date +
                ", reg_id='" + reg_id + '\'' +
                ", up_date=" + up_date +
                ", up_id='" + up_id + '\'' +
                '}';
    }

    public static class Builder {
        // PK
        private Integer ord_prod_seq; // 주문 상품 번호

        // FK
        private Integer ord_seq; // 주문 번호
        private String isbn; // 도서 pk
        private String prod_type_code; // 상품 유형 코드

        // NOT NULL
        private String ord_stat; // 주문 상태
        private String deli_stat; // 배송 상태
        private String pay_stat; // 결제 상태

        // NULL
        private Integer item_quan; //주문 상품 수량
        private Double point_perc; // 적립율
        private Integer point_pric; // 적립금
        private Integer basic_pric; // 상품 정가
        private Double bene_perc; // 상품 할인율
        private Integer bene_pric; // 상품 할인 가격
        private Integer sale_pric; // 상품 판매 가격
        private Integer ord_pric; // 주문 금액

        // 시스템 컬럼
        private Date reg_date; // 최초 등록 일시
        private String reg_id; // 최초 등록 id
        private Date up_date; // 최근 수정 일시
        private String up_id; // 최근 수정 id

        public Builder ord_prod_seq(Integer ord_prod_seq) {
            this.ord_prod_seq = ord_prod_seq;
            return this;
        }

        public Builder ord_seq(Integer ord_seq) {
            this.ord_seq = ord_seq;
            return this;
        }

        public Builder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public Builder prod_type_code(String prod_type_code) {
            this.prod_type_code = prod_type_code;
            return this;
        }

        public Builder ord_stat(String ord_stat) {
            this.ord_stat = ord_stat;
            return this;
        }

        public Builder deli_stat(String deli_stat) {
            this.deli_stat = deli_stat;
            return this;
        }

        public Builder pay_stat(String pay_stat) {
            this.pay_stat = pay_stat;
            return this;
        }

        public Builder item_quan(Integer item_quan) {
            this.item_quan = item_quan;
            return this;
        }

        public Builder point_perc(Double point_perc) {
            this.point_perc = point_perc;
            return this;
        }

        public Builder point_pric(Integer point_pric) {
            this.point_pric = point_pric;
            return this;
        }

        public Builder basic_pric(Integer basic_pric) {
            this.basic_pric = basic_pric;
            return this;
        }

        public Builder bene_perc(Double bene_perc) {
            this.bene_perc = bene_perc;
            return this;
        }

        public Builder bene_pric(Integer bene_pric) {
            this.bene_pric = bene_pric;
            return this;
        }

        public Builder sale_pric(Integer sale_pric) {
            this.sale_pric = sale_pric;
            return this;
        }

        public Builder ord_pric(Integer ord_pric) {
            this.ord_pric = ord_pric;
            return this;
        }

        public Builder reg_id(String reg_id) {
            this.reg_id = reg_id;
            return this;
        }

        public Builder reg_date(Date reg_date) {
            this.reg_date = reg_date;
            return this;
        }

        public Builder up_id(String up_id) {
            this.up_id = up_id;
            return this;
        }

        public Builder up_date(Date up_date) {
            this.up_date = up_date;
            return this;
        }

        public OrderProductDto build() {
            OrderProductDto orderProductDto = new OrderProductDto();

            orderProductDto.ord_prod_seq = this.ord_prod_seq;
            orderProductDto.ord_seq = this.ord_seq;
            orderProductDto.isbn = this.isbn;
            orderProductDto.prod_type_code = this.prod_type_code;
            orderProductDto.ord_stat = this.ord_stat;
            orderProductDto.deli_stat = this.deli_stat;
            orderProductDto.pay_stat = this.pay_stat;
            orderProductDto.item_quan = this.item_quan;
            orderProductDto.point_perc = this.point_perc;
            orderProductDto.point_pric = this.point_pric;
            orderProductDto.basic_pric = this.basic_pric;
            orderProductDto.bene_perc = this.bene_perc;
            orderProductDto.bene_pric = this.bene_pric;
            orderProductDto.sale_pric = this.sale_pric;
            orderProductDto.ord_pric = this.ord_pric;
            orderProductDto.reg_date = this.reg_date;
            orderProductDto.reg_id = this.reg_id;
            orderProductDto.up_date = this.up_date;
            orderProductDto.up_id = this.up_id;

            return orderProductDto;
        }
    }

    public Integer getOrd_prod_seq() {
        return ord_prod_seq;
    }

    public void setOrd_prod_seq(Integer ord_prod_seq) {
        this.ord_prod_seq = ord_prod_seq;
    }

    public Integer getOrd_seq() {
        return ord_seq;
    }

    public void setOrd_seq(Integer ord_seq) {
        this.ord_seq = ord_seq;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getProd_type_code() {
        return prod_type_code;
    }

    public void setProd_type_code(String prod_type_code) {
        this.prod_type_code = prod_type_code;
    }

    public String getOrd_stat() {
        return ord_stat;
    }

    public void setOrd_stat(String ord_stat) {
        this.ord_stat = ord_stat;
    }

    public String getDeli_stat() {
        return deli_stat;
    }

    public void setDeli_stat(String deli_stat) {
        this.deli_stat = deli_stat;
    }

    public String getPay_stat() {
        return pay_stat;
    }

    public void setPay_stat(String pay_stat) {
        this.pay_stat = pay_stat;
    }

    public Integer getItem_quan() {
        return item_quan;
    }

    public void setItem_quan(Integer item_quan) {
        this.item_quan = item_quan;
    }

    public Double getPoint_perc() {
        return point_perc;
    }

    public void setPoint_perc(Double point_perc) {
        this.point_perc = point_perc;
    }

    public Integer getPoint_pric() {
        return point_pric;
    }

    public void setPoint_pric(Integer point_pric) {
        this.point_pric = point_pric;
    }

    public Integer getBasic_pric() {
        return basic_pric;
    }

    public void setBasic_pric(Integer basic_pric) {
        this.basic_pric = basic_pric;
    }

    public Double getBene_perc() {
        return bene_perc;
    }

    public void setBene_perc(Double bene_perc) {
        this.bene_perc = bene_perc;
    }

    public Integer getBene_pric() {
        return bene_pric;
    }

    public void setBene_pric(Integer bene_pric) {
        this.bene_pric = bene_pric;
    }

    public Integer getSale_pric() {
        return sale_pric;
    }

    public void setSale_pric(Integer sale_pric) {
        this.sale_pric = sale_pric;
    }

    public Integer getOrd_pric() {
        return ord_pric;
    }

    public void setOrd_pric(Integer ord_pric) {
        this.ord_pric = ord_pric;
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
}
