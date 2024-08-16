package com.fastcampus.ch4.service.qa;


import com.fastcampus.ch4.dao.global.CodeDaoImp;
import com.fastcampus.ch4.dao.qa.QaCategoryDao;
import com.fastcampus.ch4.dao.qa.QaDao;
import com.fastcampus.ch4.dao.qa.QaDaoImp;
import com.fastcampus.ch4.dto.global.CodeDto;
import com.fastcampus.ch4.dto.qa.QaCategoryDto;
import com.fastcampus.ch4.dto.qa.QaDto;
import com.fastcampus.ch4.domain.qa.SearchCondition;
import com.fastcampus.ch4.dto.qa.QaStateDto;
import java.sql.SQLOutput;
import java.util.List;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QaServiceImp implements QaService {

    private final QaDaoImp qaDao;
    private final QaCategoryDao qaCategoryDao;
    private final CodeDaoImp codeDao;
    // private final QaStateDao qaStateDao;
    // private final AnswerDao answerDao;

    @Autowired
    public QaServiceImp(QaDaoImp qaDao, QaCategoryDao qaCategoryDao, CodeDaoImp codeDao) {
        this.qaDao = qaDao;
        this.qaCategoryDao = qaCategoryDao;
        this.codeDao = codeDao;
    }

    /** 1차 기능 요구 사항 정리
     * - (0) 카운팅
     * - (1) 특정 글 상세 조회
     * - (2) 글 목록 조회 - 페이징 처리, 페이징 처리 및 특정 상태
     * - (3) 글 검색 - 기간, 제목 대상으로 글 조회
     * - (4) 글 작성 - 같은 제목 작성 방지,
     * - (5) 글 삭제 - 글 번호로 삭제, 글 제목으로 삭제
     * - (6) 글 수정
     */

    // (0) 카운팅
    @Override
    public int count(String userId) {
        return qaDao.count(userId);
    }

    @Override
    public int count(String userId, SearchCondition sc) {
        return qaDao.countBySearchCondition(userId, sc);
    }

    @Override
    public List<CodeDto> readAllCategory(String cateNum) {
        return codeDao.selectByCate(cateNum); // 01
    }

    // (1) ⚙️ 특정 글 상세 조회(시퀀스라 테스트 하기 어려움)
    @Override
    public QaDto readDetail(int qaNum) {
        // ⚙️ 추후에 관련 답글 긁어 오는 거 처리하기
        return qaDao.select(qaNum);
    }

    // (2) 글 목록 조회 - 페이징 처리, 페이징 처리 및 특정 상태
    @Override
    public List<QaDto> read(String userId) {
        return qaDao.selectByUserId(userId);
    }

    @Override
    public List<QaDto> read(String userId, SearchCondition sc) {
        return qaDao.selectByUserIdAndPh(userId, sc);
    }

    // (3) 글 검색 - 기간, 제목 대상으로 글 조회
    @Override
    public List<QaDto> readBySearchCondition(String userId, SearchCondition sc) {
        return qaDao.selectBySearchCondition(userId, sc);
    }

    // (4) 글 작성 - 같은 제목 작성 방지
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean write(String userId, QaDto dto) {
        // 카테고리 값 유효한지 확인 - 통합 코드 테이블에서 조회
        CodeDto found = codeDao.selectByCode(dto.getQa_cate_num());
        System.out.println("found = " + found);
        if (found == null) return false;

        // 현재 작성한 문의글과 중복되는 제목이 있는지 확인
        QaDto isDuplicated = qaDao.selectByTitle(userId, dto.getTitle());

        // 중복된 제목이 있으면 등록 실패
        if (isDuplicated != null) return false;

        // 문의글 등록
        int rowCnt = qaDao.insert(dto);

        // 방금 등록한 Qa의 번호 조회 - 이 부분 max() + 1로 바꾸기, 이 부분 수정 해야함...
        int qaNum = qaDao.selectMaxQaSeq();


        // 상태 DTO 생성 및 등록, 이 상태 코드 테이블에서 읽어다가 사용할 수 있게 만들기 💥 - 통합 코드 테이블에서 조회
        QaStateDto state = new QaStateDto("처리 대기중", qaNum, "qa-stat-01");
        rowCnt += qaDao.insertState(state);
        return rowCnt == 2;

    }

    // (5) 글 삭제 - 글 번호로 삭제, 글 제목으로 삭제
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(QaDto dto) {
        // 문의글과 관련된 테이블 데이터 부터 삭제
        // 상태
        int rowCnt = qaDao.deleteStateByQaNum(dto.getQa_num());
        // 문의글 삭제
        rowCnt += qaDao.delete(dto);
        return rowCnt == 2;
    }


    // (6) 글 수정
    @Override
    public boolean modify(String userId, QaDto dto, SearchCondition sc) {
        System.out.println(dto);

        // 카테고리 값 유효한지 확인
        QaCategoryDto found = qaCategoryDao.select(dto.getQa_cate_num());
        System.out.println(found);
        if (found == null) return false;

        // 현재 작성한 문의글과 중복되는 제목이 있는지 확인
        QaDto isDuplicated = qaDao.selectByTitle(userId, dto.getTitle());
        System.out.println(isDuplicated);

        // 중복된 제목이 있으면 등록 실패
        if (isDuplicated != null) return false;

        // 중복되는 글이 있음 -> 작성하지 않음, 없으면 -> 작성
        System.out.println("go update!");
        int rowCnt = 0;
        try {
            rowCnt = qaDao.update(dto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return rowCnt == 1;
    }

    @Override
    public List<QaStateDto> readAllState() {
        return qaDao.selectAllState();
    }

    public int countByState(String userId, String qaCateCode) {
        return qaDao.countByState(userId, qaCateCode);
    }

    public List<QaDto> readByState(String userId, String qaCateCode, SearchCondition sc) {
        return qaDao.selectByState(userId, qaCateCode, sc);
    }

}
