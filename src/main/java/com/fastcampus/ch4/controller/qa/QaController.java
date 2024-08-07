package com.fastcampus.ch4.controller.qa;


import com.fastcampus.ch4.service.qa.QaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

@Validated // 유효성 검증
@Controller
public class QaController {

    private final QaService service;

    @Autowired
    public QaController(QaService service) {
        this.service = service;
    }

    /**
     *  1차 기능 요구 사항 정리
     *  - (1) 유저 관련 문의글 카운팅
     *  - (2) 유저 관련 문의글 조회
     *  - (3) 유저 관련 문의글 중에서 특정 키워드로 검색(기간, 제목, 기간&제목)
     *  - (4) 특정 문의글 상세 페이지 이동
     *  - (5) 유저 문의글 작성 페이지 이동
     *  - (6) 유저 문의글 작성
     *  - (7) 유저 문의글 삭제
     *  - (8) 유저 문의글 수정
     **/
}
