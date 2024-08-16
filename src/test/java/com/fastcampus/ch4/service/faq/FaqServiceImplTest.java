package com.fastcampus.ch4.service.faq;

import com.fastcampus.ch4.dao.faq.FaqCateDao;
import com.fastcampus.ch4.dto.faq.FaqCateDto;
import com.fastcampus.ch4.dto.faq.FaqDto;
import com.fastcampus.ch4.exception.NotFoundException;
import com.fastcampus.ch4.exception.UnauthorizedAccessException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

//import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class FaqServiceImplTest {
    @Autowired
    FaqService faqService;
    @Autowired
    private FaqCateDao faqCateDao;

    // 게시글 총 건수 확인
    @Test
    public void 게시글_건수_확인() {
        // 디비 데이터 모두 삭제 -> 0건
        String admin_id = "110111";
        faqService.removeAll(admin_id);
        assertTrue(faqService.count() == 0);

        // 게시글 1건 등록 후 건수 -> 1건
        FaqDto faqDto = new FaqDto("1020", "faq title", "faq content", "Y", "110111");
        assertTrue(faqService.write(faqDto) == 1);
        assertTrue(faqService.count() == 1);
    }

    /*
    # faq_seq로 게시글 1건 가져오기

    ## 게시글 읽기 - 성공
    - 성공조건 : 가져온 게시글이 저장한 게시글과 동일 && 조회수 +1
    1. 디비 데이터 전체 삭제 후 전체 건수 확인 0건
    2. 게시글 1건 등록
    3. 게시글 리스트 가져와서 게시글 번호 획득 -> faqDto에 저장(setFaq_seq)
    4. faqDto의 조회수 0 저장
    5. read(faq_seq)로 게시글 읽기 -> faqDto와 동일 && 조회수 +1건이면 성공

    ## 게시글 읽기 - 실패
    - 실패조건 : 없는 게시글 조회하면 예외 발생
    1. 디비 데이터 전체 삭제 후 전체 건수 확인 0건
    2. 게시글 1건 등록
    4. 없는 번호로 게시글 읽기 -> 예외 발생
     */

    // 게시글 1건 가져오기 - 성공
    @Test
    public void 게시글_읽기_성공() {
        // 1. 디비 데이터 전체 삭제 후 전체 건수 확인 0건
        String admin_id = "110111";
        faqService.removeAll(admin_id);
        assertTrue(faqService.count() == 0);

        // 2. 게시글 1건 등록
        FaqDto faqDto = new FaqDto("1020", "faq title", "faq content", "Y", "110111");
        assertTrue(faqService.write(faqDto) == 1);

        // 3. 게시글 리스트 가져와서 게시글 번호 획득 -> faqDto에 저장(setFaq_seq)
        FaqDto faqDtoSelected = faqService.readAll().get(0);
        int faq_seq = faqDtoSelected.getFaq_seq();
        faqDto.setFaq_seq(faq_seq);
        faqDto.setReg_date(faqDtoSelected.getReg_date());

        // 4. faqDto의 조회수 0 저장
        faqDto.setView_cnt(0);

        // 5. read(faq_seq)로 게시글 읽기 -> faqDto와 동일한지 확인 && 조회수 변동 없는지 확인
        FaqDto faqDtoRead = faqService.read(faq_seq);
        assertTrue(faqDtoRead.equals(faqDto));
        assertTrue(faqDtoRead.getView_cnt() == faqDto.getView_cnt());

        // 6. 게시글 다시 읽어와서 조회수 +1 됐는지 확인
        assertTrue(faqService.read(faq_seq).getView_cnt() == faqDto.getView_cnt() + 1);
    }

    // 게시글 1건 가져오기 - 실패
    @Test
    public void 게시글_읽기_실패() {
        // 1. 디비 데이터 전체 삭제 후 전체 건수 확인 0건
        String admin_id = "110111";
        faqService.removeAll(admin_id);
        assertTrue(faqService.count() == 0);

        // 2. 게시글 1건 등록
        FaqDto faqDto = new FaqDto("1020", "faq title", "faq content", "Y", "110111");
        assertTrue(faqService.write(faqDto) == 1);

        // 3. 없는 게시글 번호로 게시글 읽기 -> NotFoundException 발생
        assertThrows(NotFoundException.class, () -> faqService.read(-1));
    }

    /*
    # 등록된 게시글 전체 목록 가져오기
    - 공통작업 : 디비 데이터 전체 삭제 후 전체 건수 확인 0건

    ## 등록된 게시글 1건
    1. 게시글 1건 등록
    2. 전체 게시글 목록 가져오기
    3. 전체 게시글 목록 건수 확인 -> 1건
    4. 가져온 게시글 내용 확인 -> 등록한 게시글과 동일
        - 가져온 게시글의 게시글 일련번호 처음 등록한 게시글의 일련번호로 저장
        - equals로 동일한지 확인

    ## 등록된 게시글 0건
    1. 전체 게시글 목록 가져오기
    2. 전체 게시글 목록 건수 확인 -> 0건
     */
    // 등록된 게시글 전체 목록 가져오기 - 1건
    @Test
    public void 게시글_전체_목록_1건() {
        // given
        // 1. 디비 데이터 전체 삭제 후 0건 확인
        String admin_id = "110111";
        faqService.removeAll(admin_id);
        assertTrue(faqService.count() == 0);
        // 2. 게시글 1건 등록
        FaqDto faqDto = new FaqDto("1020", "faq title", "faq content", "Y", "110111");
        assertTrue(faqService.write(faqDto) == 1);

        // when
        // 1. 전체 게시글 목록 가져오기
        List<FaqDto> faqDtoAll = faqService.readAll();
        // 2. 저장된 게시글 일련번호 -> 처음 저장한 게시글 일련번호로 저장
        FaqDto faqDtoSelected = faqDtoAll.get(0);
        int faq_seq = faqDtoSelected.getFaq_seq();
        faqDto.setFaq_seq(faq_seq);
        faqDto.setReg_date(faqDtoSelected.getReg_date());

        // then
        // 1. 전체 게시글 건수 확인 -> 1건
        assertTrue(faqDtoAll.size() == 1);
        // 2. 목록으로 가져온 게시글과 처음 저장한 게시글 동일한지 확인
        assertTrue(faqDtoAll.get(0).equals(faqDto));
    }

    // 등록된 게시글 전체 목록 가져오기 - 0건 (등록된 게시글 없음)
    @Test
    public void 게시글_전체_목록_0건() {
        // given
        // 1. 디비 데이터 전체 삭제 후 0건 확인
        String admin_id = "110111";
        faqService.removeAll(admin_id);
        assertTrue(faqService.count() == 0);

        // when - then
        // 1. 전체 게시글 목록 가져와서 건수 확인 - 1건
        assertTrue(faqService.readAll().size() == 0);
    }

    // 등록된 게시글 전체 목록 가져오기 - 10건
    @Test
    public void 게시글_전체_목록_10건() {
        // given
        // 1. 디비 데이터 전체 삭제 후 0건 확인
        String admin_id = "110111";
        faqService.removeAll(admin_id);
        assertTrue(faqService.count() == 0);
        // 2. 게시글 1건 등록
        List<FaqDto> faqList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            FaqDto faqDto = new FaqDto("1020", "faq title" + i, "faq content", "Y", "110111");
            assertTrue(faqService.write(faqDto) == 1);
            faqList.add(faqDto);
        }

        // when - then
        // 1. 전체 게시글 목록 가져와서 건수 확인 - 10건
        List<FaqDto> selected = faqService.readAll();
        assertTrue(selected.size() == 10);

        // 등록할 때 쓴 게시글 리스트가 디비에서 가져온 게시글 리스트에 모두 포함되어 있는지 확인하기!!!!!!
    }

    /*
    # 게시글 쓰기
    1. 디비 데이터 전체 삭제 후 전체 건수 확인 0건
    2. 게시글 1건 등록
    3. 게시글 리스트 가져와서 게시글 번호 획득(faq_seq) -> faqDto에 저장(setFaq_seq)
    4. faq_seq로 게시글 읽기 후 내용 제대로 저장됐는지 확인
     */

    // 게시글 쓰기 - 성공
    @Test
    public void 게시글_쓰기_성공() {
        // 1. 디비 데이터 전체 삭제 후 0건 확인
        String admin_id = "110111";
        faqService.removeAll(admin_id);
        assertTrue(faqService.count() == 0);
        // 2. 게시글 1건 등록
        FaqDto faqDto = new FaqDto("1020", "faq title", "faq content", "Y", "110111");
        assertTrue(faqService.write(faqDto) == 1);

        // 3. 게시글 리스트 가져와서 게시글 번호 획득(faq_seq) -> faqDto에 저장(setFaq_seq)
        FaqDto faqDtoSelected = faqService.readAll().get(0);
        int faq_seq = faqDtoSelected.getFaq_seq();
        faqDto.setFaq_seq(faq_seq);
        faqDto.setReg_date(faqDtoSelected.getReg_date());

        // 4. faq_seq로 게시글 읽기 후 내용 제대로 저장됐는지 확인
        assertTrue(faqService.read(faq_seq).equals(faqDto));
    }

    // 게시글 쓰기 - 실패

    /*
    # 게시글 삭제
    ## 권한 있는 admin_id로 삭제 - 성공

    ## 동일한 admin_id로 삭제 - 성공

    ## 권한 없고 다른 admin_id로 삭제 - 실패
     */

    // 게시글 삭제 성공 - 등록한 id 다르지만 권한 있는 admin_id로 게시글 삭제
    @Test
    public void 게시글_삭제_성공1() {
        // 1. 디비 데이터 전체 삭제 후 0건 확인
        String adminIdAuth = "110111";
        faqService.removeAll(adminIdAuth);
        assertTrue(faqService.count() == 0);

        // 2. 게시글 1건 등록
        String adminId = "220111";
        FaqDto faqDto = new FaqDto("1020", "faq title", "faq content", "Y", adminId);
        assertTrue(faqService.write(faqDto) == 1);

        // 3. 게시글 리스트 가져와서 게시글 번호 획득(faq_seq)
        int faq_seq = faqService.readAll().get(0).getFaq_seq();

        // 4. 권한 있는 adminId와 faq_seq로 게시글 삭제
        assertTrue(faqService.remove(faq_seq, adminIdAuth) == 1);

        // 5. faq_seq로 조회했을 때 NotFoundException 예외 발생
        assertThrows(NotFoundException.class, () -> faqService.read(faq_seq));
    }

    // 게시글 삭제 성공 - 등록한 id 동일하고 권한 없는 admin_id로 게시글 삭제
    @Test
    public void 게시글_삭제_성공2() {
        // 1. 디비 데이터 전체 삭제 후 0건 확인
        String adminIdAuth = "110111";
        faqService.removeAll(adminIdAuth);
        assertTrue(faqService.count() == 0);

        // 2. 게시글 1건 등록
        String adminId = "220111";
        FaqDto faqDto = new FaqDto("1020", "faq title", "faq content", "Y", adminId);
        assertTrue(faqService.write(faqDto) == 1);

        // 3. 게시글 리스트 가져와서 게시글 번호 획득(faq_seq)
        int faq_seq = faqService.readAll().get(0).getFaq_seq();

        // 4. 권한 있는 adminId와 faq_seq로 게시글 삭제
        assertTrue(faqService.remove(faq_seq, adminId) == 1);

        // 5. faq_seq로 조회했을 때 NotFoundException 예외 발생
        assertThrows(NotFoundException.class, () -> faqService.read(faq_seq));
    }

    // 게시글 삭제 실패 - 등록한 id 다르고 권한 없는 admin_id로 게시글 삭제
    @Test
    public void 게시글_삭제_실패1() {
        // 1. 디비 데이터 전체 삭제 후 0건 확인
        String adminIdAuth = "110111";
        faqService.removeAll(adminIdAuth);
        assertTrue(faqService.count() == 0);

        // 2. 게시글 1건 등록
        String adminId = "220111";
        FaqDto faqDto = new FaqDto("1020", "faq title", "faq content", "Y", adminId);
        assertTrue(faqService.write(faqDto) == 1);

        // 3. 게시글 리스트 가져와서 게시글 번호 획득(faq_seq)
        int faq_seq = faqService.readAll().get(0).getFaq_seq();

        // 4. 권한 없는 adminId와 faq_seq로 게시글 삭제 -> UnauthorizedAccessException 예외 발생
        assertThrows(UnauthorizedAccessException.class, () -> faqService.remove(faq_seq, adminId + "X"));
    }

    // 게시글 삭제 실패 - 없는 게시글 삭제
    @Test
    public void 게시글_삭제_실패2() {
        // 1. 디비 데이터 전체 삭제 후 0건 확인
        String adminIdAuth = "110111";
        faqService.removeAll(adminIdAuth);
        assertTrue(faqService.count() == 0);

        // 2. 게시글 1건 등록
        String adminId = "220111";
        FaqDto faqDto = new FaqDto("1020", "faq title", "faq content", "Y", adminId);
        assertTrue(faqService.write(faqDto) == 1);

        // 3. 게시글 리스트 가져와서 게시글 번호 획득(faq_seq)
        int faq_seq = faqService.readAll().get(0).getFaq_seq();

        // 4. 권한 있는 adminId와 faq_seq로 게시글 삭제
        assertTrue(faqService.remove(faq_seq, adminIdAuth) == 1);

        // 5. 등록한 id로 삭제 시도했을 때 NotFoundException 예외 발생
        assertThrows(NotFoundException.class, () -> faqService.remove(faq_seq, adminId));
    }


    // 게시글 전체 삭제 - 성공 : 관리자 권한 O (1)
    @Test
    public void 게시글_전체_삭제_성공() {
        // 1. 게시글 10건 저장
        FaqDto faqDto;
        for (int i = 0; i < 10; i++) {
            faqDto = new FaqDto("1020", "faq title" + i, "faq content", "Y", "110111");
            assertTrue(faqService.write(faqDto) == 1);
        }

        // 2. 게시글 전체 삭제
        String admin_id = "110111";
        faqService.removeAll(admin_id);

        // 3. 게시글 개수 0건인지 확인
        assertTrue(faqService.count() == 0);
    }

    // 게시글 전체 삭제 - 실패 : 관리자 권한 X (1아님)
    @Test
    public void 게시글_전체_삭제_실패() {
        // 1. 게시글 10건 저장
        FaqDto faqDto;
        for (int i = 0; i < 10; i++) {
            faqDto = new FaqDto("1020", "faq title" + i, "faq content", "Y", "110111");
            assertTrue(faqService.write(faqDto) == 1);
        }

        // 2. 권한 없는 id로 게시글 전체 삭제 -> UnauthorizedAccessException 예외 발생
        String admin_id = "220111";
        assertThrows(UnauthorizedAccessException.class, () -> faqService.removeAll(admin_id));
    }

    // 게시글 수정 - 성공
    @Test
    public void 게시글_수정_성공() {
        // 1. 디비 데이터 전체 삭제 후 0건 확인
        String admin_id = "110111";
        faqService.removeAll(admin_id);
        assertTrue(faqService.count() == 0);

        // 2. 게시글 1건 등록
        String adminId = "220111";
        FaqDto faqDto = new FaqDto("001020", "faq title", "faq content", "Y", adminId);
        assertTrue(faqService.write(faqDto) == 1);

        // 3. 게시글 리스트 가져와서 게시글 번호 획득(faq_seq)
        FaqDto faqDtoSelected = faqService.readAll().get(0);
        int faq_seq = faqDtoSelected.getFaq_seq();

        faqDto.setFaq_seq(faq_seq); // faq_seq 저장
        faqDto.setReg_date(faqDtoSelected.getReg_date()); // reg_date 저장

        // 4. content, adminId 바꿔서 faqDto 작성 -> 해당 faqDto로 내용 수정
        faqDto.setCont("faq content update");
        String adminIdUpdate = "330111";
        faqDto.setUp_id(adminIdUpdate); // adminId 바꿔서 Update
        assertTrue(faqService.modify(faqDto) == 1);

        // 5. 내용 조회해서 faqDto와 동일한지 확인 equals
        FaqDto faqDtoRead = faqService.read(faq_seq);
        assertTrue(faqDtoRead.equals(faqDto));
        // 6. up_id와 up_date 바뀌었는지 확인
        assertTrue(faqDtoRead.getUp_id().equals(adminIdUpdate));
    }

    // 게시글 수정 - 실패 : 없는 게시글 수정
    public void 게시글_수정_실패1() {
        // 1. 디비 데이터 전체 삭제 후 0건 확인
        // 2. 게시글 1건 등록
        // 3. 게시글 리스트 가져와서 게시글 번호 획득(faq_seq)
        // 4. 게시글 번호 바꿔서 faqDto 작성 -> 해당 faqDto로 내용 수정 시도하면 실패
        // 5. DB에 그대로 남아있는지 확인
    }

    // 게시글 수정 - 실패 : 관리자 ID가 아닌 경우
    public void 게시글_수정_실패2() {
        // 1. 디비 데이터 전체 삭제 후 0건 확인
        // 2. 게시글 1건 등록
        // 3. 게시글 리스트 가져와서 게시글 번호 획득(faq_seq)
        // 4. 관리자 아닌 id로 id 바꿔서 faqDto 작성 -> 해당 faqDto로 내용 수정 시도하면 실패
        // 5. DB에 그대로 남아있는지 확인
    }

    // 게시글 검색 - 성공
    @Test
    public void 게시글_검색_성공() {
        // 게시글 전체 삭제 후 게시글 1건 등록
        // 키워드로 게시글 검색 -> 검색된 건수 확인
    }

    // 게시글 카테고리별 조회 - 성공
    @Test
    public void 카테고리별_게시글_목록_조회_성공() {
        // 카테고리 코드 전체 삭제 후 코드 등록
        faqCateDao.deleteAll();

        // 카테고리 코드 등록 -> 카테고리 코드 : 10, 카테고리 이름 : 회원
        String CODE_10 = "10";
        String CODE_10_NAME = "회원";
        FaqCateDto faqCateDto = new FaqCateDto(CODE_10, CODE_10_NAME, 'Y', "110111");
        assertTrue(faqCateDao.insert(faqCateDto) == 1);

        // 카테고리 코드 등록 -> 카테고리 코드 : 1010, 카테고리 이름 : 회원가입/탈퇴
        String CODE_1010 = "1010";
        String CODE_1010_NAME = "회원가입/탈퇴";
        faqCateDto = new FaqCateDto(CODE_1010, CODE_1010_NAME, 'Y', "110111");
        assertTrue(faqCateDao.insert(faqCateDto) == 1);


        // 게시글 전체 삭제 후 게시글 3건 등록
        String admin_id = "110111";
        faqService.removeAll(admin_id);
        assertTrue(faqService.count() == 0);

        // 카테고리 다르게 게시글 넣기
        for (int i = 1; i <= 3; i++) {
            FaqDto faqDto = new FaqDto(CODE_10 + 10*i, "faq title" + i, "faq content", "Y", "110111");
            assertTrue(faqService.write(faqDto) == 1);
        }

        // catg_name "회원" 넣으면 게시글 목록 3개
        assertTrue(faqService.readByCatgCode(CODE_10).size() == 3);

        // catg_name "회원가입/탈퇴" 넣으면 게시글 목록 1개
        assertTrue(faqService.readByCatgCode(CODE_1010).size() == 1);
    }

    // 게시글 카테고리별 조회 - 실패 : 카테고리 코드가 유효하지 않으면 예외 발생
    @Test
    public void 카테고리별_게시글_목록_조회_실패() {
        // 카테고리 코드 테이블 데이터 전체 삭제
        faqCateDao.deleteAll();

        // 게시글 전체 삭제 후 게시글 등록
        String admin_id = "110111";
        faqService.removeAll(admin_id);
        assertTrue(faqService.count() == 0);

        String CODE_10 = "10";
        FaqDto faqDto = new FaqDto(CODE_10, "faq title", "faq content", "Y", "110111");
        assertTrue(faqService.write(faqDto) == 1);

        // 유효하지 않은 코드로 목록 조회 -> NotFoundException 발생
        assertThrows(NotFoundException.class, () -> faqService.readByCatgCode("-1"));
    }


    /*


    # admin_id로 게시글 삭제
    - 성공조건 : feq_seq 게시글의 reg_id가 삭제를 시도하는 adminId와 동일해야 삭제 가능

    ## admin_id로 게시글 삭제 - 성공
    1. 디비 데이터 전체 삭제 후 전체 건수 확인 0건
    2. 게시글 1건 등록
    3. 게시글 리스트 가져와서 게시글 번호 획득(faq_seq)
    4. 동일한 adminId와 faq_seq로 게시글 삭제
    5. faq_seq로 조회했을 때 IllegalArgumentException 예외 발생

    ## admin_id로 게시글 삭제 - 실패
    1. 디비 데이터 전체 삭제 후 전체 건수 확인 0건
    2. 게시글 1건 등록
    3. 게시글 리스트 가져와서 게시글 번호 획득(faq_seq)
    4. 다른 adminId와 faq_seq로 게시글 삭제
    5. faq_seq로 조회했을 때 존재하는지 확인

    // 게시글 삭제
    @Test
    public void admin_id로_게시글_삭제_성공() {
        // 1. 디비 데이터 전체 삭제 후 0건 확인
        String admin_id = "110111";
        faqService.removeAll(admin_id);
        assertTrue(faqService.count() == 0);
        // 2. 게시글 1건 등록
        String adminId = "220111";
        FaqDto faqDto = new FaqDto("1020", "faq title", "faq content", "Y", adminId);
        assertTrue(faqService.write(faqDto) == 1);

        // 3. 게시글 리스트 가져와서 게시글 번호 획득(faq_seq)
        int faq_seq = faqService.readAll().get(0).getFaq_seq();

        // 4. 동일한 adminId와 faq_seq로 게시글 삭제
        assertTrue(faqService.removeByRegId(faq_seq, adminId) == 1);

        // 5. faq_seq로 조회했을 때 IllegalArgumentException 예외 발생
        assertThrows(IllegalArgumentException.class, () -> faqService.read(faq_seq));
    }

    // 게시글 삭제 실패
    @Test
    public void admin_id로_게시글_삭제_실패() {
        // 1. 디비 데이터 전체 삭제 후 0건 확인
        String admin_id = "110111";
        faqService.removeAll(admin_id);
        assertTrue(faqService.count() == 0);
        // 2. 게시글 1건 등록
        String adminId = "110111";
        FaqDto faqDto = new FaqDto("1020", "faq title", "faq content", "Y", adminId);
        assertTrue(faqService.write(faqDto) == 1);

        // 3. 게시글 리스트 가져와서 게시글 번호 획득(faq_seq)
        FaqDto faqDtoSelected = faqService.readAll().get(0);
        int faq_seq = faqDtoSelected.getFaq_seq();
        faqDto.setFaq_seq(faq_seq);
        faqDto.setReg_date(faqDtoSelected.getReg_date());

        // 4. 다른 adminId와 faq_seq로 게시글 삭제 -> 예외 발생
        assertThrows(IllegalArgumentException.class, () -> faqService.removeByRegId(faq_seq, adminId + "X"));

        // 5. faq_seq로 조회했을 때 존재하는지 확인
        assertTrue(faqService.read(faq_seq).equals(faqDto));
    }
     */
}