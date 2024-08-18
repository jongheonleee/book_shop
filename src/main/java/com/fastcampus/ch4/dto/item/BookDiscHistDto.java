package com.fastcampus.ch4.dto.item;

import java.util.Date;

public class BookDiscHistDto {
    private String isbn;
    private int disc_seq; //할인_일련번호
    private double papr_disc;//종이책_할인율
    private double e_disc;//ebook_할인율
    private Date disc_start_date;//할인시작일자
    private Date disc_end_date;//할인종료일자
    private String comt;//비고
    private Date reg_date;//최초등록일시
    private String reg_id;//최초등록자식별번호
    private Date up_date;//최초수정일시
    private String up_id;//최종수정자식별번호
}
