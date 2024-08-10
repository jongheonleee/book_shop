package com.fastcampus.ch4.service.qa;

import com.fastcampus.ch4.dto.qa.QaDto;
import com.fastcampus.ch4.domain.qa.SearchCondition;
import java.util.List;

public interface QaService {

    // (0) 카운팅
    int count(String userId);

    // (1) ⚙️ 특정 글 상세 조회(시퀀스라 테스트 하기 어려움)
    QaDto readDetail(int qaNum);

    // (2) 글 목록 조회 - 페이징 처리, 페이징 처리 및 특정 상태
    List<QaDto> read(String userId);

    List<QaDto> read(String userId, SearchCondition sc);

    // (3) 글 검색 - 기간, 제목 대상으로 글 조회 -> 💥페이징 처리 안됨..
    List<QaDto> readBySearchCondition(String userId, SearchCondition sc);

    // (4) 글 작성 - 같은 제목 작성 방지
    boolean write(String userId, SearchCondition sc, QaDto dto);

    // (5) 글 삭제 - 글 번호로 삭제, 글 제목으로 삭제
    boolean remove(QaDto dto);

    // (6) 글 수정
    boolean modify(String userId, SearchCondition sc, QaDto dto);
}
