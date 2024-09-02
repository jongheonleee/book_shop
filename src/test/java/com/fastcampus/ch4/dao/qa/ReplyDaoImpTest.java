package com.fastcampus.ch4.dao.qa;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fastcampus.ch4.dto.qa.QaDto;
import com.fastcampus.ch4.dto.qa.ReplyDto;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class ReplyDaoImpTest {

    @Autowired
    private ReplyDaoImp replyDao;

    @Autowired
    private QaDaoImp qaDaoImp;

    @Before
    public void setUp() {
        assertTrue(replyDao != null);
        assertTrue(qaDaoImp != null);

        replyDao.deleteAll();
        qaDaoImp.deleteAll();
    }

    /**
     * 기능 요구 사항 정리
     * - (1) 답변을 등록한다
     * - not null 필드에 null이 들어간 경우, 예외가 발생함
     * - 내용이 공백이면 예외가 발생함(최소 길이 3이상)
     * - 관리자가 아닌 경우, 등록할 수 없음
     * - 존재하지 않는 문의글 번호로 등록
     * - 반복(중복) 등록 실패
     * - 답변 등록 성공
     *
     * - (2) 전체 답변을 카운팅한다
     * - 답변이 없는 경우에는 0을 카운팅
     * - 답변이 있는 경우, 해당 개수만큼 카운팅(현재는 1개만 등록)
     *
     * - (3) 특정 답변을 조회한다
     * - 없을 때, 조회하면 null을 반환
     * - 있을 때, 조회하면 1개의 답변을 반환 & 내용 비교
     *
     * - (4) 모든 답변을 조회한다
     * - 없을 때, 조회하면 null을 반환
     * - 있을 때, 조회하면 1개의 답변을 반환 & 내용 비교
     *
     * - (5) 특정 답변을 업데이트한다
     * - 없을 때, 업데이트하면 0 반환
     * - 있을 때, 업데이트하면 1 반환, 내용 비교
     * - 있을 때, 업데이트했지만 not null 필드가 null인 경우 DataIntegrityViolation 발생
     * - 있을 때, 업데이트했지만 내용 필드가 공백인 경우 DataIntegrityViolation 발생
     *
     * - (6) 특정 답변을 삭제한다
     * - 없을 때, 삭제하면 0
     * - 있을 때, 삭제하면 1
     *
     * - (7) 모든 답변을 삭제한다
     * - 답변이 없는 경우 삭제하면 0
     * - 답변이 1개 있는 경우 삭제하면 1
     */

    // 01. 답변을 등록한다
    @Test
    public void not_null_필드_null_값() {
        // 더미 데이터를 생성한다
        QaDto qa = createQa();
        assertTrue(1 == qaDaoImp.insert(qa));
        QaDto found = qaDaoImp.selectByTitle("user1", qa.getTitle());
        ReplyDto dto = createReply(found.getQa_num());

        // 더미 데이터에 not null 필드 null 값 넣음
        dto.setContent(null);

        // dao를 통해 insert를 시도함
        // 예외가 발생해야한다 (DataIntegrityViolationException)
        assertThrows(DataIntegrityViolationException.class, () -> {
            replyDao.insert(dto);
        });
    }

    @Test
    public void 내용_공백() {
        // 더미 데이터를 생성한다
        QaDto qa = createQa();
        assertTrue(1 == qaDaoImp.insert(qa));
        QaDto found = qaDaoImp.selectByTitle("user1", qa.getTitle());
        ReplyDto dto = createReply(found.getQa_num());


        // 더미 데이터에 내용을 공백으로 넣음
        dto.setContent("");

        // dao를 통해 insert를 시도함
        // 예외가 발생해야한다 (DataIntegrityViolationException)
        assertThrows(UncategorizedSQLException.class, () -> {
            replyDao.insert(dto);
        });
    }

    @Test
    public void 관리자_아닌_경우() {
        // 해당 부분은 서비스 단에서 작업 처리
    }

    @Test
    public void 반복_등록() {
        // 더미 데이터를 생성한다
        // dao를 통해 insert를 시도함
        QaDto qa = createQa();
        assertTrue(1 == qaDaoImp.insert(qa));
        QaDto found = qaDaoImp.selectByTitle("user1", qa.getTitle());
        ReplyDto dto = createReply(found.getQa_num());

        // 같은 데이터를 dao로 다시 insert를 시도함
        // 이 과정에서 예외가 발생한다(DuplicatedKeyException)
        assertTrue(1 == replyDao.insert(dto));
        assertThrows(DuplicateKeyException.class, () -> {
            replyDao.insert(dto);
        });
    }

    @Test
    public void 존재하지_않는_문의글_번호_등록() {
        // 더미 데이터를 생성한다
        QaDto qa = createQa();
        assertTrue(1 == qaDaoImp.insert(qa));
        QaDto found = qaDaoImp.selectByTitle("user1", qa.getTitle());
        ReplyDto dto = createReply(-123);

        // dao를 통해 insert를 시도함
        // 예외 발생
        assertThrows(DataIntegrityViolationException.class, () -> {
            replyDao.insert(dto);
        });
    }

    @Test
    public void 등록_성공() {
        // 더미 데이터를 생성한다
        QaDto qa = createQa();
        assertTrue(1 == qaDaoImp.insert(qa));
        QaDto found = qaDaoImp.selectByTitle("user1", qa.getTitle());
        ReplyDto dto = createReply(found.getQa_num());

        // dao를 통해 insert를 시도함
        // 정상적으로 등록되어야함. 적용된 로우수 1
        assertTrue(1 == replyDao.insert(dto));
    }

    // (2) 전체 답변을 카운팅한다
    @Test
    public void 답변_없음_0() {
        // 카운팅 했을 때 0
        assertTrue(0 == replyDao.count());
    }

    @Test
    public void 답변_있음_1() {
        // 더미 데이터 생성
        QaDto qa = createQa();
        assertTrue(1 == qaDaoImp.insert(qa));
        QaDto found = qaDaoImp.selectByTitle("user1", qa.getTitle());
        ReplyDto dto = createReply(found.getQa_num());

        // 답글 등록
        assertTrue(1 == replyDao.insert(dto));

        // 카운팅 했을 때 1
        assertTrue(1 == replyDao.count());
    }

    // (3) 특정 답변을 조회한다
    @Test
    public void 없음_null() {
        // 조회, null 반환
        ReplyDto found = replyDao.select(11);
        assertNull(found);
    }

    @Test
    public void 있음_1() {
        // 더미 데이터 생성
        QaDto qa = createQa();
        assertTrue(1 == qaDaoImp.insert(qa));
        QaDto foundQa = qaDaoImp.selectByTitle("user1", qa.getTitle());
        ReplyDto dto = createReply(foundQa.getQa_num());

        // dao를 통해서 데이터 등록
        assertTrue(1 == replyDao.insert(dto));

        // dao를 통해서 조회
        ReplyDto foundRepl = replyDao.select(foundQa.getQa_num());

        // 널아님, 내용비교
        assertNotNull(foundRepl);
        assertEquals(dto, foundRepl);

    }

    // (4) 모든 답변을 조회한다
    @Test
    public void 없음_사이즈_0() {
        // 조회, 사이즈 0인 리스트 반환
        List<ReplyDto> founds = replyDao.selectAll();
        assertTrue(founds.size() == 0);
    }

    @Test
    public void 있음_사이즈_1() {
        // 더미 데이터 생성
        QaDto qa = createQa();
        assertTrue(1 == qaDaoImp.insert(qa));
        QaDto foundQa = qaDaoImp.selectByTitle("user1", qa.getTitle());
        ReplyDto dto = createReply(foundQa.getQa_num());

        // dao를 통해서 데이터 등록
        assertTrue(1 == replyDao.insert(dto));

        // dao를 통해서 조회
        List<ReplyDto> founds = replyDao.selectAll();

        // 사이즈 1인 리스트 반환, 내용비교
        assertTrue(founds.size() == 1);
        assertEquals(dto, founds.get(0));
    }

    // (5) 특정 답변을 업데이트한다
    @Test
    public void 없음_수정_0() {
        // 데이터가 없는 상황에서 수정함, 적용된 로우수 0
        replyDao.update(createReply(11));
    }

    @Test
    public void 있음_수정_1() {
        // 더미 데이터 생성
        QaDto qa = createQa();
        assertTrue(1 == qaDaoImp.insert(qa));
        QaDto foundQa = qaDaoImp.selectByTitle("user1", qa.getTitle());
        ReplyDto dto = createReply(foundQa.getQa_num());

        // dao를 통해서 데이터 등록
        assertTrue(1 == replyDao.insert(dto));

        // 더미 데이터 필드 변경
        dto.setWriter("new admin1");
        dto.setContent("수정된 답변입니다.");

        // dao를 통해서 데이터 수정, 적용된 로우수 1
        assertTrue(1 == replyDao.update(dto));

        // 내용비교
        ReplyDto updated = replyDao.select(foundQa.getQa_num());
        assertEquals(dto, updated);

    }

    @Test
    public void 있음_수정_not_null_필드_null() {
        // 더미 데이터 생성
        QaDto qa = createQa();
        assertTrue(1 == qaDaoImp.insert(qa));
        QaDto foundQa = qaDaoImp.selectByTitle("user1", qa.getTitle());
        ReplyDto dto = createReply(foundQa.getQa_num());

        // dao를 통해서 데이터 등록
        assertTrue(1 == replyDao.insert(dto));

        // 더미 데이터 필드 변경, not null 필드 null
        dto.setWriter(null);
        dto.setContent(null);

        // dao를 통해서 데이터 수정, 예외 발생
        assertThrows(DataIntegrityViolationException.class, () -> {
            replyDao.update(dto);
        });
    }

    @Test
    public void 있음_수정_내용_공백() {
        // 더미 데이터 생성
        QaDto qa = createQa();
        assertTrue(1 == qaDaoImp.insert(qa));
        QaDto foundQa = qaDaoImp.selectByTitle("user1", qa.getTitle());
        ReplyDto dto = createReply(foundQa.getQa_num());

        // dao를 통해서 데이터 등록
        assertTrue(1 == replyDao.insert(dto));

        // 더미 데이터 필드 변경, 내용 공백
        dto.setContent("");

        // dao를 통해서 데이터 수정, 예외 발생
        assertThrows(UncategorizedSQLException.class, () -> {
            replyDao.update(dto);
        });
    }


    // (6) 특정 답변을 삭제한다
    @Test
    public void 없음_삭제_0() {
        // 데이터가 없는 상태에서 모두 삭제, 적용된 로우수 0
        assertTrue(0 == replyDao.delete(111));
    }

    @Test
    public void 있음_삭제_1() {
        // 더미 데이터 생성
        QaDto qa = createQa();
        assertTrue(1 == qaDaoImp.insert(qa));
        QaDto foundQa = qaDaoImp.selectByTitle("user1", qa.getTitle());
        ReplyDto dto = createReply(foundQa.getQa_num());

        // dao를 통해서 데이터 등록
        assertTrue(1 == replyDao.insert(dto));

        // 해당 데이터 삭제, 적용된 로우수 1
        assertTrue(1 == replyDao.delete(foundQa.getQa_num()));

        // 조회했을 때 null 반환
        assertNull(replyDao.select(foundQa.getQa_num()));
    }


    // (7) 모든 답변을 삭제한다
    @Test
    public void 없음_모두_삭제_0() {
        // 데이터가 없는 상태에서 모두 삭제, 적용된 로우수 0
        assertTrue(0 == replyDao.deleteAll());
    }

    @Test
    public void 있음_모두_삭제_1() {
        // 더미 데이터 생성
        QaDto qa = createQa();
        assertTrue(1 == qaDaoImp.insert(qa));
        QaDto foundQa = qaDaoImp.selectByTitle("user1", qa.getTitle());
        ReplyDto dto = createReply(foundQa.getQa_num());

        // dao를 통해서 데이터 등록
        assertTrue(1 == replyDao.insert(dto));

        // 해당 데이터 삭제, 적용된 로우수 1
        assertTrue(1 == replyDao.deleteAll());

        // 조회했을 때 사이즈 0
        List<ReplyDto> founds = replyDao.selectAll();
        assertTrue(founds.size() == 0);
    }

    private ReplyDto createReply(int qaNum) {
        ReplyDto dto = new ReplyDto();
        dto.setQa_num(qaNum);
        dto.setWriter("admin1");
        dto.setContent("답변입니다.");
        dto.setCreted_at("2021-01-01");
        dto.setComt("비고 사항입니다.");
        dto.setReg_date("2021-01-01");
        dto.setUp_date("2021-01-01");
        dto.setUp_id("admin1");
        return dto;
    }

    private QaDto createQa() {
        // 카테고리, 상태 모두 존재하는지 확인
        QaDto dto = new QaDto();
        dto.setUser_id("user1");
        dto.setQa_cate_num("qa-cate-01");
        dto.setCate_name("배송/수령예정일안내");
        dto.setQa_stat_code("qa-stat-01");
        dto.setStat_name("처리 대기중");
        dto.setChk_repl("Y");
        dto.setTitle("테스트용 문의글입니다.");
        dto.setContent("바람이 세차게 불어오는 저녁, 해변을 따라 걷던 엘레나는 발밑에서 부서지는 파도 소리를 들으며 잠시 멈춰 섰다. 하늘은 붉은 노을에 물들어 있었고, 태양은 서서히 수평선 아래로 사라지고 있었다. 그녀는 손을 주머니에 넣고, 바다를 응시하며 깊은 생각에 잠겼다. 몇 년 전, 이곳에서의 추억들이 그녀의 마음속에 선명하게 떠올랐다. 그때는 모든 것이 단순하고 아름다웠다. 하지만 지금, 시간은 모든 것을 변화시키고, 사람들 사이에 놓인 거리는 점점 더 멀어져만 갔다. 엘레나는 서늘한 바람에 얼굴을 내맡기며, 아직 끝나지 않은 이야기를 다시 써 내려갈 용기를 다짐했다. 그녀의 발걸음은 다시 앞으로 향했고, 바다는 여전히 그녀의 곁에서 잔잔하게 출렁이고 있었다.");
        dto.setCreated_at("2021-01-01");
        dto.setEmail("email1");
        dto.setTele_num("010-1234-5678");
        dto.setPhon_num("010-1234-5678");
        dto.setImg1("img1");
        dto.setImg2("img2");
        dto.setImg3("img3");
        return dto;
    }
}