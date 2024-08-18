package com.fastcampus.ch4.service.faq;

import com.fastcampus.ch4.dao.admin.AdminDao;
import com.fastcampus.ch4.dao.faq.FaqCateDao;
import com.fastcampus.ch4.dao.faq.FaqDao;
import com.fastcampus.ch4.domain.faq.SearchCondition;
import com.fastcampus.ch4.dto.admin.AdminDto;
import com.fastcampus.ch4.dto.faq.FaqCateDto;
import com.fastcampus.ch4.dto.faq.FaqDto;
import com.fastcampus.ch4.exception.NotFoundException;
import com.fastcampus.ch4.exception.SaveFailedException;
import com.fastcampus.ch4.exception.UnauthorizedAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional   // transactional???  데이터 1건만 읽는 것도 트랜잭셔널 필요??
@Service
public class FaqServiceImpl implements FaqService {
    String LIST_NOT_FOUND_MSG = "게시물 목록을 가져오는데 실패했습니다.";
    String READ_NOT_FOUND_MSG = "삭제되었거나 없는 게시글입니다.";
    String DEL_NOT_FOUND_MSG = "이미 삭제되었거나 없는 게시글입니다";
    String READ_UN_AUTH_MSG = "읽기 권한이 없습니다.";  // 관리자 아닌 사람이 노출 N인 게시글 보려고 할 때 사용 예정
    String DEL_UN_AUTH_MSG = "삭제 권한이 없습니다.";
    String MODIFY_UN_AUTH_MSG = "글 수정 권한이 없습니다.";
    String WRITE_UN_AUTH_MSG = "글 작성 권한이 없습니다.";
    String SAVE_ERR_MSG = "게시글 저장에 실패했습니다.";

    @Autowired
    FaqDao faqDao;

    @Autowired
    AdminDao adminDao;

    @Autowired
    FaqCateDao faqCateDao;

    // 전체 게시글 건수 확인
    @Override
    public int count() {
        return faqDao.count();
    }

    // 게시글 읽는 기능
    @Override
    public FaqDto read(int faq_seq) {
        // 1. 게시글 번호로 읽기 select(faq_seq)
        FaqDto faqDto = faqDao.select(faq_seq);
        // 2. 게시글 없으면 예외 날리기
        if (faqDto == null) {
            throw new NotFoundException(READ_NOT_FOUND_MSG);
        }
        // 3. 게시글 가져오기 성공하면 조회수 1 증가
        faqDao.increaseViewCnt(faq_seq);
        return faqDto;
    }

    // 게시글 전체 목록 읽기
    @Transactional(readOnly = true)
    @Override
    public List<FaqDto> readAll() {
        return faqDao.selectAll();
    }

    // 노출여부 Y인 게시글만 읽기
    @Transactional(readOnly = true)
    @Override
    public List<FaqDto> readDisplay() {
        return faqDao.selectDisplay();
    }

    // 게시글 등록 기능
    @Override
    public int write(FaqDto faqDto) {
        // 등록하려는 Id가 관리자 테이블에 있는지 확인 -> 관리자 아니면 예외 발생
        AdminDto admin = adminDao.select(faqDto.getReg_id());
        if (admin == null) {
            throw new UnauthorizedAccessException(WRITE_UN_AUTH_MSG);
        }

        // 게시글 노출 여부가 Y가 아니면 N으로 저장
        if (!"Y".equals(faqDto.getDsply_chk())) {
            faqDto.setDsply_chk("N");
        }

        try {
            return faqDao.insert(faqDto);
        } catch (DataIntegrityViolationException ex) {
            throw new SaveFailedException(SAVE_ERR_MSG, ex);
        }
    }

    // 게시글 삭제 기능 - 등록한 사람이거나 관리자 권한 있으면(1) 삭제 가능
    @Override
    public int remove(int faq_seq, String admin_id) {
        // 삭제하려는 Id가 관리자 테이블에 있는지 확인 -> 관리자 아니면 예외 발생
        AdminDto admin = adminDao.select(admin_id);
        if (admin == null) {
            throw new UnauthorizedAccessException(DEL_UN_AUTH_MSG);
        }

        // 게시글이 없으면 예외 발생
        if (faqDao.select(faq_seq) == null) {
            throw new NotFoundException(DEL_NOT_FOUND_MSG);
        }

        // 찾은 관리자에게 권한(1)이 있으면 게시글 삭제
        if (admin.getAuth() == 1) {
            return faqDao.delete(faq_seq);
        }

        // 넘어온 관리자 id와 게시글의 등록자 id가 다르면 예외 발생
        String reg_id = faqDao.select(faq_seq).getReg_id();
        if (!admin_id.equals(reg_id)) {
            throw new UnauthorizedAccessException(DEL_UN_AUTH_MSG);
        }

        // 넘어온 관리자 id와 게시글 등록자 id 같으면 삭제 실행
        return faqDao.deleteByRegId(faq_seq, admin_id);
    }

    // 게시글 전체 삭제 기능 - 관리자 권한 있어야 전체 삭제 가능
    @Override
    public int removeAll(String admin_id) {
        // adminId가 권한(1)이 없으면 삭제 불가 예외 발생
        int auth = adminDao.select(admin_id).getAuth();
        if (auth != 1) {
            throw new UnauthorizedAccessException(DEL_UN_AUTH_MSG);
        }
        return faqDao.deleteAll();
    }

    // 게시글 수정 기능
    @Override
    public int modify(FaqDto faqDto) {
        String upId = faqDto.getUp_id();

        // 삭제하려는 Id가 관리자 테이블에 있는지 확인 -> 관리자 아니면 예외 발생
        AdminDto admin = adminDao.select(upId);
        if (admin == null) {
            throw new UnauthorizedAccessException(MODIFY_UN_AUTH_MSG);
        }

        // 게시글이 없으면 예외 발생
        if (faqDao.select(faqDto.getFaq_seq()) == null) {
            throw new NotFoundException(DEL_NOT_FOUND_MSG);
        }

        try {
            return faqDao.update(faqDto);
        } catch (DataIntegrityViolationException ex) {
            throw new SaveFailedException(SAVE_ERR_MSG, ex);
        }
    }

    // 키워드로 게시글 검색 기능
    @Override
    public List<FaqDto> searchByKeyword(SearchCondition condition) {
        return faqDao.search(condition);
    }

    // 특정 카테고리의 게시글 목록 읽기
    @Transactional(readOnly = true)
    @Override
    public List<FaqDto> readByCatgCode(String faq_catg_code) {
        // faq_cate 테이블에서 catg_name에 해당하는 catg_code 찾기
        FaqCateDto faqCateDto = faqCateDao.selectByCode(faq_catg_code);

        // faq_catg_code가 없는 카테고리 코드인 경우 예외 발생
        if (faqCateDto == null) {
            throw new NotFoundException(LIST_NOT_FOUND_MSG);
        }

        // null 아니면 catg_code로 게시글 목록 불러오기
        return faqDao.selectByCatg(faq_catg_code);
    }
}
