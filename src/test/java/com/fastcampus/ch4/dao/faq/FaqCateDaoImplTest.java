package com.fastcampus.ch4.dao.faq;

import com.fastcampus.ch4.dto.faq.FaqCateDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class FaqCateDaoImplTest {
    @Autowired
    FaqCateDao faqCateDao;

    @Test
    public void count() {
        faqCateDao.deleteAll();
        assertTrue(faqCateDao.count() == 0);
    }

    // 저장 실패 - 이미 DB에 존재하는 ID(PK) 값으로 저장 시도하는 경우, DuplicateKeyException(SQLIntegrityConstraintViolationException - JDBC 예외)
    @Test
    public void 카테고리_저장_실패() {
        faqCateDao.deleteAll();
        assertTrue(faqCateDao.count() == 0);

        // 카테고리 저장
        String CATE_CODE = "001010";
        FaqCateDto dto = new FaqCateDto(CATE_CODE, "회원정보", 'Y', "110111");
        assertTrue(faqCateDao.insert(dto) == 1);

        // 동일한 PK로 카테고리 저장 시 예외 발생
        assertThrows(DuplicateKeyException.class, () -> faqCateDao.insert(dto));

        // 저장된 건수 1건인지 확인
        assertTrue(faqCateDao.count() == 1);
    }

    @Test
    public void 카테고리_이름으로_읽기() {
        // 모두 삭제 후 1건 넣기
        faqCateDao.deleteAll();
        FaqCateDto faqCateDto = new FaqCateDto("1010", "회원탈퇴", 'Y', "110111");
        assertTrue(faqCateDao.insert(faqCateDto) == 1);

        // 저장된 카테고리 이름 가져와서 이름으로 읽기
        String name = faqCateDao.selectAll().get(0).getName();
        FaqCateDto faqCateDtoSelect = faqCateDao.selectByName(name);

        // 읽은 값이 저장한 값과 동일한지 확인
        faqCateDto.setReg_date(faqCateDao.selectAll().get(0).getReg_date());
        assertTrue(faqCateDtoSelect.equals(faqCateDto));
    }

    @Test
    public void 카테고리_코드로_읽기() {
        // 모두 삭제 후 1건 넣기
        faqCateDao.deleteAll();
        FaqCateDto faqCateDto = new FaqCateDto("1010", "회원탈퇴", 'Y', "110111");
        assertTrue(faqCateDao.insert(faqCateDto) == 1);

        // 저장된 카테고리 코드 가져와서 코드로 읽기
        String cate_code = faqCateDao.selectAll().get(0).getCate_code();
        FaqCateDto faqCateDtoSelect = faqCateDao.selectByCode(cate_code);

        // 읽은 값이 저장한 값과 동일한지 확인
        faqCateDto.setReg_date(faqCateDao.selectAll().get(0).getReg_date());
        assertTrue(faqCateDtoSelect.equals(faqCateDto));
    }

    @Test
    public void 상위_카테고리로_하위_카테고리_읽기() {
        // 모두 삭제 후 2건 넣기
        faqCateDao.deleteAll();
        String CATE_CODE_0010 = "0010";
        FaqCateDto faqCateDto = new FaqCateDto(CATE_CODE_0010, "회원", 'Y', "110111");
        String CATE_CODE_001010 = "001010";
        assertTrue(faqCateDao.insert(faqCateDto) == 1);
        faqCateDto = new FaqCateDto(CATE_CODE_001010, "회원탈퇴", 'Y', "110111");
        assertTrue(faqCateDao.insert(faqCateDto) == 1);

        // "0010" 카테고리 코드로 읽으면 결과 2건
        List<FaqCateDto> list = faqCateDao.selectChildByParent(CATE_CODE_0010);
        assertTrue(list.size() == 1);
    }

    @Test
    public void 상위_카테고리와_그_하위_카테고리_읽기() {
        // 모두 삭제 후 2건 넣기
        faqCateDao.deleteAll();
        FaqCateDto faqCateDto = new FaqCateDto("0010", "회원", 'Y', "110111");
        assertTrue(faqCateDao.insert(faqCateDto) == 1);
        faqCateDto = new FaqCateDto("001010", "회원탈퇴", 'Y', "110111");
        assertTrue(faqCateDao.insert(faqCateDto) == 1);

        // "0010" 카테고리 코드로 읽으면 결과 2건
        List<FaqCateDto> list = faqCateDao.selectChildAndParent("0010");
        assertTrue(list.size() == 2);
    }

    // 하위 카테고리만 가져오기
    @Test
    public void sub_카테고리만_읽기() {
        // 모두 삭제 후 2건 넣기
        faqCateDao.deleteAll();
        FaqCateDto faqCateDto = new FaqCateDto("0010", "회원", 'Y', "110111");
        assertTrue(faqCateDao.insert(faqCateDto) == 1);
        faqCateDto = new FaqCateDto("001010", "회원탈퇴", 'Y', "110111");
        assertTrue(faqCateDao.insert(faqCateDto) == 1);

        // 하위 카테고리 읽으면 1건
        List<FaqCateDto> list = faqCateDao.selectSubCate();
        assertTrue(list.size() == 1);
    }

    // selectMainCate Test
    @Test
    public void main_카테고리만_읽기() {
        // 모두 삭제 후 2건 넣기
        faqCateDao.deleteAll();
        FaqCateDto faqCateDto = new FaqCateDto("0010", "회원", 'Y', "110111");
        assertTrue(faqCateDao.insert(faqCateDto) == 1);
        faqCateDto = new FaqCateDto("001010", "회원탈퇴", 'Y', "110111");
        assertTrue(faqCateDao.insert(faqCateDto) == 1);

        // main 카테고리 읽으면 1건
        List<FaqCateDto> list = faqCateDao.selectMainCate();
        assertTrue(list.size() == 1);
    }

    // insert Test
    // 카테고리 입력했을 때
    @Test
    public void 카테고리_입력() {
        // 모두 삭제
        faqCateDao.deleteAll();

        // 1건 넣기
        FaqCateDto faqCateDto = new FaqCateDto("001010", "회원탈퇴", 'Y', "110111");
        assertTrue(faqCateDao.insert(faqCateDto) == 1);

        // 카테고리 데이터 건수 1
        assertTrue(faqCateDao.count() == 1);

        // 입력한 카테고리 Dto와 동일한지 확인 equals()
        String cateCode = faqCateDao.selectAll().get(0).getCate_code();
        FaqCateDto selected = faqCateDao.selectByCode(cateCode);

        faqCateDto.setParent_code(selected.getParent_code());
        faqCateDto.setReg_date(selected.getReg_date());

        assertTrue(selected.equals(faqCateDto));
    }

    // update Test
}