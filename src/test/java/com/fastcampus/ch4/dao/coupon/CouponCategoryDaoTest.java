package com.fastcampus.ch4.dao.coupon;

import static org.junit.jupiter.api.Assertions.*;

import com.fastcampus.ch4.dto.coupon.CouponCategoryDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class CouponCategoryDaoTest {

    @Autowired
    CouponCategoryDaoImp couponCateDao;

    @Before
    public void 주입_테스트() {
        assertNotNull(couponCateDao);
        couponCateDao.deleteAll();
    }

    /**
     * 기능 목록
     * 1. 모든 쿠폰 카테고리 개수를 카운팅한다
     * - 0개 일 때, 0개 카운팅
     * - 등록한 개수 만큼 카운팅(n개)
     *
     * 2. 모든 쿠폰 카테고리를 조회한다
     * - 0개 일 때, null 반환(이거 예외로 다루기)
     * - n개 일 때, n개 반환(사이즈)
     *
     * 3. 대-중분류를 조인해서 조회한다
     * - 0개 일 때, null 반환(이거 예외로 다루기)
     * - n개 일 때, n개 반환(사이즈, 제목 비교)
     *
     * 4. 카테고리 코드로 조회한다(5번이랑 합치기)
     * - 0개 일 때, null 반환(이거 예외로 다루기)
     * - 대분류 01을 키워드로 검색했을 때 관련 카테고리 조회
     *
     * 5. 카테고리 코드를 키워드로 조회한다
     * - 대분류 01을 키워드로 검색했을 때 관련 카테고리 조회
     *
     * 6. 카테고리를 수정한다
     *
     *
     * 7. 모든 카테고리를 삭제한다
     * - 0개 등록, 0개 삭제
     * - n개 만큼 등록하면 n개 만큼 삭제
     *
     * 8. 특정 카테고리를 삭제한다
     * - 0 개 등록, 0개 삭제
     * - 특정 카테고리 삭제 성공
     *
     * 9. 특정 카테고리를 등록한다
     * - not null 필드 null -> DataIntegrityViolationException
     * - 공백이 들어감 ->  UncategorizedSQLException
     * - 중복된 카테고리 코드 등록 -> DataIntegrityViolationException
     * - n개 등록 성공
     *
     */

    // 1. 모든 쿠폰 카테고리 개수를 카운팅한다
    @Test
    public void count1() {
        assertTrue(0 == couponCateDao.count());
    }

    @Test
    public void count2() {
        CouponCategoryDto dto = create(1);
        assertTrue(1 == couponCateDao.insert(dto));
        assertTrue(1 == couponCateDao.count());
    }

    // 2. 모든 쿠폰 카테고리를 조회한다
    @Test
    public void select1() {
        List<CouponCategoryDto> selected = couponCateDao.selectAll();
        assertTrue(selected.size() == 0);
    }

    @Test
    public void select2() {
        // given : 대분류/중분류 삽입 - 대분류 3, 중분류 3
        List<CouponCategoryDto> 대분류 = 대분류_생성();
        List<CouponCategoryDto> 중분류 = 중분류_생성();

        for (CouponCategoryDto dto : 대분류) {
            assertTrue(1 == couponCateDao.insert(dto));
        }

        for (CouponCategoryDto dto : 중분류) {
            assertTrue(1 == couponCateDao.insert(dto));
        }

        // when : 조회
        List<CouponCategoryDto> selected = couponCateDao.selectAll();

        // then : 등록한 개수만큼 사이즈 반환, 내용 비교
        assertTrue(selected.size() == 6);

    }

    // 3. 대-중분류를 조인해서 조회한다
    @Test
    public void selectAllByJoin1() {
        List<CouponCategoryDto> selected = couponCateDao.selectAllByJoin();
        assertTrue(selected.size() == 0);
    }


    @Test
    public void selectAllByJoin2() {
        List<CouponCategoryDto> 대분류 = 대분류_생성();
        List<CouponCategoryDto> 중분류 = 중분류_생성();

        for (CouponCategoryDto dto : 대분류) {
            assertTrue(1 == couponCateDao.insert(dto));
        }

        for (CouponCategoryDto dto : 중분류) {
            assertTrue(1 == couponCateDao.insert(dto));
        }

        List<CouponCategoryDto> selected = couponCateDao.selectAllByJoin();
        selected.forEach(dto -> System.out.println(dto));
    }

    // 4. 카테고리 코드로 조회한다
    @Test
    public void selectByCateCode1() {
        List<CouponCategoryDto> selected = couponCateDao.selectByCateCode("");
        assertTrue(selected.size() == 0);
    }

    @Test
    public void selectByCateCode2() {
        List<CouponCategoryDto> 대분류 = 대분류_생성();
        List<CouponCategoryDto> 중분류 = 중분류_생성();

        for (CouponCategoryDto dto : 대분류) {
            assertTrue(1 == couponCateDao.insert(dto));
        }

        for (CouponCategoryDto dto : 중분류) {
            assertTrue(1 == couponCateDao.insert(dto));
        }

        List<CouponCategoryDto> selected = couponCateDao.selectByCateCode(대분류.get(0).getTop_cate_code());
        assertTrue(selected.size() == 4);
    }

    // 6. 카테고리를 수정한다
    @Test
    public void update1() {
        CouponCategoryDto dto = create(1);
        assertTrue(1 == couponCateDao.insert(dto));
        dto.setName("수정된 이름");
        assertTrue(1 == couponCateDao.update(dto));
    }

    @Test
    public void update2() {
        CouponCategoryDto dto = create(1);
        assertTrue(1 == couponCateDao.insert(dto));
        dto.setName("");
        assertThrows(UncategorizedSQLException.class, () -> couponCateDao.update(dto));
    }

    @Test
    public void update3() {
        CouponCategoryDto dto = create(1);
        assertTrue(1 == couponCateDao.insert(dto));
        dto.setName(null);
        assertThrows(DataIntegrityViolationException.class, () -> couponCateDao.update(dto));
    }




    // 9. 특정 카테고리를 등록한다
    @Test
    @DisplayName("not null 필드 null -> DataVioletionException")
    public void insert1() {
        CouponCategoryDto dto = create(1);
        dto.setCate_code(null);
        assertThrows(DataIntegrityViolationException.class, () -> couponCateDao.insert(dto));
    }

    @Test
    @DisplayName("공백이 들어감 ->  DataVioletionException")
    public void insert2() {
        CouponCategoryDto dto = create(1);
        dto.setName("");
        assertThrows(UncategorizedSQLException.class, () -> couponCateDao.insert(dto));
    }

    @Test
    @DisplayName("중복된 카테고리 코드 등록 -> DuplicatedKeyException")
    public void insert3() {
        CouponCategoryDto dto = create(1);
        System.out.println(dto);
        assertTrue(1 == couponCateDao.insert(dto));
        assertThrows(DuplicateKeyException.class, () -> couponCateDao.insert(dto));
    }

    @DisplayName("카테고리 등록 성공 ")
    @Test
    public void insert4() {
        CouponCategoryDto dto = create(1);
        int result = couponCateDao.insert(dto);
        assertEquals(1, result);
    }

    // 7. 모든 카테고리를 삭제한다
    @Test
    public void deleteAll1() {
        assertTrue(0 == couponCateDao.deleteAll());
    }

    @Test
    public void deleteAll2() {
        CouponCategoryDto dto = create(1);
        assertTrue(1 == couponCateDao.insert(dto));

        dto = create(2);
        assertTrue(1 == couponCateDao.insert(dto));

        dto = create(3);
        assertTrue(1 == couponCateDao.insert(dto));
        assertTrue(3 == couponCateDao.deleteAll());
    }


    // 8. 특정 카테고리를 삭제한다
    @Test
    public void delete1() {
        assertTrue(0 == couponCateDao.delete(""));
    }

    @Test
    public void delete2() {
        CouponCategoryDto dto = create(1);
        assertTrue(1 == couponCateDao.insert(dto));
        assertTrue(1 == couponCateDao.delete(dto.getCate_code()));
    }




    private CouponCategoryDto create(int i) {
        CouponCategoryDto dto = new CouponCategoryDto();
        dto.setCate_code("0101" + i);
        dto.setName("온라인 상품" + i);
        dto.setComt("...");
        dto.setTop_cate_code("01");
        return dto;
    }

    private List<CouponCategoryDto> 중분류_생성() {
        List<CouponCategoryDto> list = new ArrayList<>();

        CouponCategoryDto dto = new CouponCategoryDto();
        dto.setCate_code("01");
        dto.setName("온라인");
        dto.setComt("...");
        dto.setTop_cate_code(null);
        list.add(dto);

        dto = new CouponCategoryDto();
        dto.setCate_code("02");
        dto.setName("오프라인");
        dto.setComt("...");
        dto.setTop_cate_code(null);
        list.add(dto);

        dto = new CouponCategoryDto();
        dto.setCate_code("03");
        dto.setName("통합");
        dto.setComt("...");
        dto.setTop_cate_code(null);
        list.add(dto);

        return list;
    }

    private List<CouponCategoryDto> 대분류_생성() {
        List<CouponCategoryDto> list = new ArrayList<>();

        CouponCategoryDto dto = new CouponCategoryDto();
        dto.setCate_code("0101");
        dto.setName("상품");
        dto.setComt("...");
        dto.setTop_cate_code("01");
        list.add(dto);

        dto = new CouponCategoryDto();
        dto.setCate_code("0102");
        dto.setName("주문");
        dto.setComt("...");
        dto.setTop_cate_code("01");
        list.add(dto);

        dto = new CouponCategoryDto();
        dto.setCate_code("0103");
        dto.setName("배송");
        dto.setComt("...");
        dto.setTop_cate_code("01");
        list.add(dto);

        return list;
    }

}