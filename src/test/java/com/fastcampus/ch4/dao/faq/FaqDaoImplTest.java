package com.fastcampus.ch4.dao.faq;

import com.fastcampus.ch4.domain.SearchCondition;
import com.fastcampus.ch4.dto.faq.FaqDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class FaqDaoImplTest {

    @Autowired
    FaqDao faqDao;

    // title순 정렬 기준
    Comparator<FaqDto> comparatorTitle = new Comparator<FaqDto>() {
        @Override
        public int compare(FaqDto o1, FaqDto o2) {
            return o2.getTitle().compareTo(o1.getTitle());
        }
    };

    @Before
    public void init() {
        // DB 내용 전체 삭제 후 총 개수 0개 확인
        faqDao.deleteAll();
        assertTrue(faqDao.count() == 0);
    }

    // 게시글 DB에 저장하는 기능
    @Test
    public void 게시글_저장_성공1() {
        // 1개의 게시글 저장
        FaqDto faqDto = new FaqDto("1020", "faq title", "faq content", "Y", "110111");
        assertTrue(faqDao.insert(faqDto) == 1);

        // 게시글 개수 검색 결과 1
        System.out.println(faqDao.count());
        assertTrue(faqDao.count() == 1);

        // 게시글 불러와서 내용이 제대로 들어갔는지 확인
        FaqDto faqDtoSelected = faqDao.selectAll().get(0);
        int faqSeq = faqDtoSelected.getFaq_seq();
        faqDto.setFaq_seq(faqSeq);
        faqDto.setReg_date(faqDtoSelected.getReg_date());

        FaqDto faqDto2 = faqDao.select(faqSeq);
        assertTrue(faqDto2.equals(faqDto));
    }

    // 게시글 DB에 저장하는 기능
    @Test
    public void 게시글_저장_성공10() {
        // 10개의 게시글 저장 & LIST에 담기
        List<FaqDto> faqDtoList = new ArrayList<>();
        FaqDto faqDto;
        for (int i = 0; i < 10; i++) {
            faqDto = new FaqDto("1020", "faq title" + i, "faq content" + i, "Y", "110111");
            assertTrue(faqDao.insert(faqDto) == 1);
            faqDtoList.add(faqDto);
        }

        // 게시글 전체 불러와서 10개 제대로 저장됐는지 확인
            // count 건수 10개인지 확인
        assertTrue(faqDao.count() == 10);
            // 불러온 목록과 저장한 LIST 동일한지 확인 (정렬필요)

        // 저장한 LIST title 순으로 정렬
        Collections.sort(faqDtoList, comparatorTitle);

        // 불러와서 title 순으로 정렬
        List<FaqDto> faqDtoListSelected = faqDao.selectAll();
        Collections.sort(faqDtoListSelected, comparatorTitle);

        // list 동일한지 확인
        for (int i = 0; i < faqDtoList.size(); i++) {
            int faq_seq = faqDtoListSelected.get(i).getFaq_seq();
            faqDtoList.get(i).setFaq_seq(faq_seq);
            Date reg_date = faqDtoListSelected.get(i).getReg_date();
            faqDtoList.get(i).setReg_date(reg_date);

            assertTrue(faqDtoList.get(i).equals(faqDtoListSelected.get(i)));
        }
    }

    // 게시글 저장 예외 - 필수 입력 항목 누락된 경우
    @Test
    public void 게시글_저장_예외1() {
        // given
        // 1. 필수 입력 항목인 제목 누락해서 faqDto 생성
        FaqDto faqDto = new FaqDto();
        faqDto.setFaq_catg_code("1010");
        faqDto.setCont("faq content");
        faqDto.setReg_id("110111");

        // when
        // 1. 해당 faqDto로 DB에 데이터 저장
        // then - DataIntegrityViolationException 예외 발생
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> faqDao.insert(faqDto));

        // 저장 안 됐는지 확인
        assertTrue(faqDao.count() == 0);
    }

    /*
    1. 게시글 저장 시 발생하는 예외
    2. 발생 이유 : Integer 타입의 필드에 String이 넘어온 경우
    3. 발생 예외 : org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property='catg', mode=IN, javaType=class java.lang.Integer, jdbcType=null, numericScale=null, resultMapId='null', jdbcTypeName='null', expression='null'}. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: java.lang.ClassCastException: class java.lang.String cannot be cast to class java.lang.Integer (java.lang.String and java.lang.Integer are in module java.base of loader 'bootstrap')
    4. 발생 유도 : FaqDaoImpl에서 String 값을 넘기도록 메서드 작성 (테스트용 메서드 별도 작성)
    5. 테스트용 메서드 별도 작성 이유 : 기존 메서드에서 테스트 시 타입 불일치로 컴파일 되지 않음
    6. 질문 : 이렇게 컴파일용 메서드를 따로 작성하기도 하는지?

    7. 테스트용 메서드 작성과 별도의 강사님 피드백 : 테스트 안 하거나, Integer에 굉장히 큰 값 넣어서 테스트
     */
    // 게시글 저장 예외 - Integer 타입의 필드에 String이 넘어온 경우
    @Test
    public void 게시글_저장_예외2() {
    }

    // 게시글 삭제 : 해당 게시글 등록한 사람만 삭제 가능
    // 게시글 삭제 성공 : 삭제하려고 하는 사람의 id와 등록자의 id(reg_id)가 동일할 때
    @Test
    public void admin_id로_게시글_삭제_성공() {
        // given - 게시글 전체 삭제 후 게시글 1개 등록
        FaqDto faqDto = new FaqDto("1020", "faq title", "faq content", "Y", "110111");
        assertTrue(faqDao.insert(faqDto) == 1);

        // when - 해당 게시글 삭제
        // 1. 전체 게시글을 가져와서 0번째 게시글의 글번호, 작성자 확인
        FaqDto faqDto2 = faqDao.selectAll().get(0);
        int faqSeq = faqDto2.getFaq_seq();
        String adminId = faqDto2.getReg_id();

        // 2. 얻은 글번호와 작성자로 게시글 삭제 (delete)
        assertTrue(faqDao.deleteByRegId(faqSeq, adminId) == 1);

        // then
        // 1. 총 게시글 개수 0개 확인
        assertTrue(faqDao.count() == 0);
        // 2. 글번호로 읽은 게시글 null인지 확인
        assertTrue(faqDao.select(faqSeq) == null);
    }

    // 게시글 삭제 실패 : 삭제하려고 하는 사람의 id와 등록자의 Id(reg_id)가 다를 때
    @Test
    public void admin_id로_게시글_삭제_실패1() {
        // given - 게시글 전체 삭제 후 게시글 1개 등록
        FaqDto faqDto = new FaqDto("1020", "faq title", "faq content", "Y", "110111");
        assertTrue(faqDao.insert(faqDto) == 1);

        // when - 해당 게시글 삭제
        // 1. 전체 게시글을 가져와서 0번째 게시글의 글번호, 작성자 확인
        FaqDto faqDto2 = faqDao.selectAll().get(0);
        int faqSeq = faqDto2.getFaq_seq();
        String adminId = faqDto2.getReg_id();

        // 2. 얻은 작성자 번호 다르게 해서 게시글 삭제 (delete) -> 결과 0
        assertTrue(faqDao.deleteByRegId(faqSeq, adminId+"X") == 0);

        // then
        // 1. 총 게시글 개수 1개 확인
        assertTrue(faqDao.count() == 1);

        // 2. 게시글 조회해서 등록한 게시글과 동일한 게시글 존재하는지 확인
        faqDto.setFaq_seq(faqSeq);
        faqDto.setReg_date(faqDto2.getReg_date());
        assertTrue(faqDao.select(faqSeq).equals(faqDto));
    }

    // 게시글 삭제 실패 : 없는 게시글을 삭제할 때
    @Test
    public void admin_id로_게시글_삭제_실패2() {
        // given
        // 1. 전체 삭제 후 게시글 1개 등록
        FaqDto faqDto = new FaqDto("1020", "faq title", "faq content", "Y", "110111");
        assertTrue(faqDao.insert(faqDto) == 1);

        // 2. 게시글 번호, 작성자 확인
        FaqDto faqDto2 = faqDao.selectAll().get(0);
        int faqSeq = faqDto2.getFaq_seq();
        String adminId = faqDto2.getReg_id();

        // 3. 전체 게시글 삭제
        faqDao.deleteAll();
        assertTrue(faqDao.count() == 0);

        // when - 확인한 게시글 번호, 작성자로 게시글 삭제 -> then 삭제된 게시글 결과 0건
        assertTrue(faqDao.deleteByRegId(faqSeq, adminId) == 0);
    }

    // 게시글 삭제
    @Test
    public void 게시글_삭제_성공() {
        // given
        // 1. 전체 삭제 후 게시글 1개 등록
        FaqDto faqDto = new FaqDto("1020", "faq title", "faq content", "Y", "110111");
        assertTrue(faqDao.insert(faqDto) == 1);

        // when - 해당 게시글 삭제
        // 1. 전체 게시글을 가져와서 0번째 게시글의 글번호 확인
        int faqSeq = faqDao.selectAll().get(0).getFaq_seq();
        // 2. 얻은 글번호로 게시글 삭제 (delete)
        assertTrue(faqDao.delete(faqSeq) == 1);

        // then
        // 1. 총 게시글 개수 0개 확인
        assertTrue(faqDao.count() == 0);
        // 2. 글번호로 읽은 게시글 null인지 확인
        assertTrue(faqDao.select(faqSeq) == null);
        // 3. 글번호로 또 삭제 시도 시 0건인지 확인
        assertTrue(faqDao.delete(faqSeq) == 0);
    }

    // 게시글 수정 성공
    @Test
    public void 게시글_수정_성공() {
        // given - 게시글 전체 삭제 후 게시글 1개 등록
        FaqDto faqDto = new FaqDto("1020", "faq title", "faq content", "Y", "110111");
        assertTrue(faqDao.insert(faqDto) == 1);

        // when - 게시글 번호로 조회해서 게시글 수정
        // 1. 전체 게시글 불러온 후 FaqDto 얻어서 게시글 번호 확인
        int faqSeq = faqDao.selectAll().get(0).getFaq_seq();

        // 2. faqDto 내용 변경 (title, content) 후 업데이트
        faqDto.setFaq_seq(faqSeq);
        faqDto.setTitle("faq title updated");
        faqDto.setCont("faq content updated");
        faqDto.setUp_id("220222");
        assertTrue(faqDao.update(faqDto) == 1);

        // then - 게시글 번호로 조회해서 변경된 내용 제대로 변경됐는지 확인
        // 1. 게시글 번호로 조회(faqDto2)해서 title, content, up_id가 동일한지 확인
        FaqDto faqDto2 = faqDao.select(faqSeq);
        assertTrue(faqDto2.getTitle().equals(faqDto.getTitle()));
        assertTrue(faqDto2.getCont().equals(faqDto.getCont()));
        assertTrue(faqDto2.getUp_id().equals(faqDto.getUp_id()));
    }

    // 게시글 수정 실패 : 없는 게시글 수정 시
    @Test
    public void 게시글_수정_실패() {
        // 글 등록 후 게시글 번호 확인
        FaqDto faqDto = new FaqDto("1020", "faq title", "faq content", "Y", "110111");
        assertTrue(faqDao.insert(faqDto) == 1);
        int faqSeq = faqDao.selectAll().get(0).getFaq_seq();

        // 게시글 번호에 해당하는 게시글 삭제
        assertTrue(faqDao.delete(faqSeq) == 1);

        // 게시글 번호에 해당하는 게시글 없는지 확인
        assertTrue(faqDao.select(faqSeq) == null);

        // faqDto 수정해서 update() 시 0건인지 확인

    }

    // 게시글 수정 예외 : not null 을 null로 바꾸는 경우

    // 게시글 번호로 게시글 읽기 - 성공
    @Test
    public void 게시글_읽기_성공() {
        // given - 게시글 전체 삭제 후 게시글 1개 등록
        FaqDto faqDto = new FaqDto("1020", "faq title", "faq content", "Y", "110111");
        assertTrue(faqDao.insert(faqDto) == 1);

        // when - 게시글 읽기
        // 1. 입력한 게시글의 게시글 번호 확인
        FaqDto faqDtoSelected = faqDao.selectAll().get(0);

        // 2. 게시글번호 설정, 등록일자 설정
        faqDto.setFaq_seq(faqDtoSelected.getFaq_seq());
        faqDto.setReg_date(faqDtoSelected.getReg_date());

        // 3. 게시글 번호로 게시글 읽기
        FaqDto faqDto2 = faqDao.select(faqDtoSelected.getFaq_seq());

        // then -  입력한 게시글과 읽은 게시글이 동일하면 성공 (equals로 동일한지 확인)
        assertTrue(faqDto.equals(faqDto2));
    }

    // 게시글 번호로 게시글 읽기 - 실패 (없는 게시글 번호로 읽으려고 할 때)
    @Test
    public void 게시글_읽기_실패() {
        // given - 게시글 전체 삭제 후 게시글 10개 등록

        for (int i = 0; i < 10; i++) {
            FaqDto faqDto = new FaqDto("1020", "faq title" + i, "faq content" + i, "Y", "110111");
            assertTrue(faqDao.insert(faqDto) == 1);
        }
        assertTrue(faqDao.count() == 10);

        // when - 없는 게시글 번호(-1)로 읽기
        // then -  읽어온 값 null
        assertTrue(faqDao.select(-1) == null);
    }

    // 등록된 게시글 전체 목록 읽기, List로 반환
    @Test
    public void 전체_게시글_읽기() {
        // given - 게시글 전체 삭제 후 게시글 10개 등록

        for (int i = 0; i < 10; i++) {
            FaqDto faqDto = new FaqDto("1020", "faq title" + i, "faq content" + i, "Y", "110111");
            assertTrue(faqDao.insert(faqDto) == 1);
        }
        assertTrue(faqDao.count() == 10);

        // when - 전체 게시글 목록 읽어오기
        // then - 목록 사이즈가 10개인지 확인
        assertTrue(faqDao.selectAll().size() == 10);

        // when, then - 전체 삭제 후 게시글 목록 사이즈 0개인지 확인
        faqDao.deleteAll();
        assertTrue(faqDao.selectAll().size() == 0);
    }

    // 키워드로 게시글 검색
    @Test
    public void 게시글_검색_성공() {
        // 게시글 9개 등록
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                FaqDto faqDto = new FaqDto("1020", "faq title" + i + j, "faq content", "Y", "110111");
                assertTrue(faqDao.insert(faqDto) == 1);
            }
        }

        /*
        title00
        title01
        title02
        title10
        title11
        title12
        title20
        title21
        title22
         */

        // 1. "e0" 검색 시, 결과 3건
        SearchCondition sc = new SearchCondition("A", "e0");
        assertTrue(faqDao.search(sc).size() == 3);

        // 2. "0" 검색 시, 결과 5건
        sc.setKeyword("0");
        assertTrue(faqDao.search(sc).size() == 5);

        // 3. "3" 검색 시, 결과 0건
        sc.setKeyword("3");
        assertTrue(faqDao.search(sc).size() == 0);

        // 4. "title" 검색 시, 결과 9건
        sc.setKeyword("title");
        assertTrue(faqDao.search(sc).size() == 9);

        // 5. ""(공란) 검색 시, 전체 게시글 검색
        sc.setKeyword("");
        assertTrue(faqDao.search(sc).size() == 9);
    }

    // 키워드로 게시글 검색 예외 - 어떤 경우가 있을까?????
    @Test
    public void 게시글_검색_예외() {

    }

    // 조회수 1 증가
    @Test
    public void 조회수_1_증가() {
        // given
        // 1. 데이터 전체 삭제, 게시글 1개 등록
        FaqDto faqDto = new FaqDto("1020", "faq title", "faq content", "Y", "110111");
        assertTrue(faqDao.insert(faqDto) == 1);

        // 2. 해당 게시글 번호 얻어오기
        int faqSeq = faqDao.selectAll().get(0).getFaq_seq();

        // 3. 얻어온 번호로 게시글 조회해서 조회수 확인 0
        FaqDto faqDtoSelect = faqDao.select(faqSeq);
        assertTrue(faqDtoSelect.getView_cnt() == 0);

        // when
        // 조회수 1 증가 실행
        faqSeq = faqDtoSelect.getFaq_seq();
        assertTrue(faqDao.increaseViewCnt(faqSeq) == 1);

        // then
        // 게시글 조회해서 조회수 확인 1
        assertTrue(faqDao.select(faqSeq).getView_cnt() == 1);
    }


    // 카테고리별_게시글_읽기_성공
    @Test
    public void 카테고리별_게시글_읽기_성공() {

        // 카테고리 다르게 게시글 넣기
        List<FaqDto> faqDtoList = new ArrayList<>();
        String faq_cate_code = "0010";
        for (int i = 1; i <= 3; i++) {
            FaqDto faqDto = new FaqDto(faq_cate_code + 10*i, "faq title" + i, "faq content", "Y", "110111");
            assertTrue(faqDao.insert(faqDto) == 1);
            faqDtoList.add(faqDto);
        }

        // 코드 10 넣으면 게시글 목록 3개
        List<FaqDto> selectList = faqDao.selectByCatg(faq_cate_code);
        assertTrue(selectList.size() == 3);
        // faqDtoList와 selectList가 같은지 확인!
        // 쿼리문에서 정렬해서 불러오기? 그냥 faq_cate_code를 다르게 넣었으니까 그걸로 선택해서 faq_seq 설정하고 equals() 확인?

        // 코드 1010 넣으면 게시글 목록 1개
        selectList = faqDao.selectByCatg(faq_cate_code + "10");
        assertTrue(selectList.size() == 1);
    }

    // 카테고리별_게시글_읽기_실패 : 없는 카테고리 코드로 게시글 읽기 시도
    @Test
    public void 카테고리별_게시글_읽기_실패() {

        // 카테고리 다르게 게시글 넣기
        for (int i = 1; i <= 3; i++) {
            FaqDto faqDto = new FaqDto("10" + 10*i, "faq title" + i, "faq content", "Y", "110111");
            assertTrue(faqDao.insert(faqDto) == 1);
        }

        // 코드 50 넣으면 게시글 목록 0개
        assertTrue(faqDao.selectByCatg("50").size() == 0);
    }
}