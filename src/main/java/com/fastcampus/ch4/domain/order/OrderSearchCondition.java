package com.fastcampus.ch4.domain.order;

/*
검색조건
1. title 검색
2. 주문번호 검색
3. 기간 별 검색
4. 주문 배송 상태 검색
검색을 요청하는 회원의 주문에 한하여 검색
 */
public class OrderSearchCondition {
    String book_title;
    Integer ord_seq;
    String start_date;
    String end_date;
    String order_status; // 주문의 상태 코드 (OrderStatus, DeliveryStatus, PaymentStatus 포함)
    String cust_id; // 검색을 요청하는 회원의 아이디 (필수)

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public Integer getOrd_seq() {
        return ord_seq;
    }

    public void setOrd_seq(Integer ord_seq) {
        this.ord_seq = ord_seq;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }
}
