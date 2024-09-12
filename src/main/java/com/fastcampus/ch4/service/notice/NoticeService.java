package com.fastcampus.ch4.service.notice;

import com.fastcampus.ch4.dto.notice.NoticeDto;
import com.fastcampus.ch4.service.admin.AdminService;

import java.util.List;
import java.util.Map;

public interface NoticeService {
    // count : 게시글 전체 건수
    int count();

    // countDsply : 노출되는 게시글 건수
    int countDsply();

    // write
    void write(NoticeDto noticeDto);

    // read : 게시글 1건 읽기
    NoticeDto read(String userId, int ntc_seq);

    // getPage
    // admin이면 노출여부 상관 없이 모두 선택, admin 아니면 노출여부 Y인 글만 선택
    List<NoticeDto> getPage(String userId, Map map);

    // modify : 게시글 수정
    void modify(NoticeDto noticeDto);

    // removeAll : 게시글 전체 삭제
    int removeAll(String userId);

    AdminService getAdminService();
}
