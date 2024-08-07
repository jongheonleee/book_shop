package com.fastcampus.ch4.service.qa;


import com.fastcampus.ch4.dao.qa.QaDao;
import com.fastcampus.ch4.dto.qa.QaDto;
import com.fastcampus.ch4.dto.qa.SearchCondition;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QaServiceImp {

    private final QaDao dao;
    // private final AnswerDao answerDao;

    @Autowired
    public QaServiceImp(QaDao dao) {
        this.dao = dao;
    }

    /** 기능 요구 사항 정리
     * - (1) 특정 글 상세 조회
     * - (2) 글 목록 조회 - 페이징 처리, 페이징 처리 및 특정 상태
     * - (3) 글 검색 - 기간, 제목 대상으로 글 조회
     * - (4) 글 작성 - 같은 제목 작성 방지,
     * - (5) 글 삭제 - 글 번호로 삭제, 글 제목으로 삭제
     * - (6) 글 수정
     */

    // (1) ⚙️ 특정 글 상세 조회(시퀀스라 테스트 하기 어려움)
    public QaDto readDetail(int qaNum) {
        // ⚙️ 추후에 관련 답글 긁어 오는 거 처리하기
        return dao.select(qaNum);
    }

    // (2) 글 목록 조회 - 페이징 처리, 페이징 처리 및 특정 상태
    public List<QaDto> read(String userId) {
        return dao.selectByUserId(userId);
    }

    public List<QaDto> read(String userId, SearchCondition sc) {
        return dao.selectByUserIdAndPh(userId, sc);
    }

    // (3) 글 검색 - 기간, 제목 대상으로 글 조회
    public List<QaDto> readBySearchCondition(String userId, SearchCondition sc) {
        return dao.selectBySearchCondition(userId, sc);
    }

    // (4) 글 작성 - 같은 제목 작성 방지
    public boolean write(String userId, SearchCondition sc, QaDto dto) {
        // 기존의 작성한 문의글 조회
        List<QaDto> selected = dao.selectBySearchCondition(userId, sc);
        // 현재 작성한 문의글과 중복되는 제목이 있는지 확인
        boolean isDuplicated = selected
                                .stream()
                                .anyMatch(o -> o.getTitle().equals(dto.getTitle()));
        // 중복되는 글이 있음 -> 작성하지 않음, 없으면 -> 작성
        return isDuplicated ? false : dao.insert(dto) == 1;
    }

    // (5) 글 삭제 - 글 번호로 삭제, 글 제목으로 삭제
    public boolean remove(QaDto dto) {
        return dao.delete(dto) == 1;
    }


    // (6) 글 수정
    public boolean modify(String userId, SearchCondition sc, QaDto dto) {
        // 기존의 작성한 문의글 조회
        List<QaDto> selected = dao.selectBySearchCondition(userId, sc);
        // 현재 수정한 문의글과 문의글 번호는 다르지만 중복되는 제목이 있는지 확인
        boolean isDuplicated = selected
                                .stream()
                                .anyMatch(o -> o.getTitle().equals(dto.getTitle()) && o.getQa_num() != dto.getQa_num());
        // 중복되는 글이 있음 -> 작성하지 않음, 없으면 -> 작성
        return isDuplicated ? false : dao.update(dto) == 1;
    }

}
