package com.fastcampus.ch4.dao.item;

import com.fastcampus.ch4.dto.item.CategoryDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class CategoryDaoImplTest {

    @Autowired
    CategoryDao categoryDao;

    @Test
    public void insertTestData() throws Exception{
        // 전체 지우고 카운트
        categoryDao.deleteAll();
        assertTrue(categoryDao.count()==0);

        // 데이터 300개 insert
        for (int i = 1; i <= 500; i++) {
            CategoryDto categoryDto = new CategoryDto("cate_num"+i, "cate_name" +i, i,
                    'Y', "cur_layr_num" + i, "whol_layr_name" + i);
            assertTrue(categoryDao.insert(categoryDto) == 1);
        }
    }

    @Test
    public void countTest() throws Exception{
        // 전체 지우고 카운트
        categoryDao.deleteAll();
        assertTrue(categoryDao.count()==0);

        // 한 개 넣고 카운트
        CategoryDto categoryDto = new CategoryDto("cate_num", "cate_name", 1,
                'Y', "cur_layr_num", "whol_layr_name");
        assertTrue(categoryDao.insert(categoryDto)==1);
        assertTrue(categoryDao.count()==1);

        // 전체 지우고 카운트
        categoryDao.deleteAll();
        assertTrue(categoryDao.count()==0);

        // 100개 넣고 카운트
        for (int i = 1; i <= 100; i++) {
            categoryDto = new CategoryDto("cate_num"+i, "cate_name" +i, i,
                    'Y', "cur_layr_num" + i, "whol_layr_name" + i);
            assertTrue(categoryDao.insert(categoryDto) == 1);
        }
        assertTrue(categoryDao.count()==100);
    }

    @Test
    public void deleteAllTest() throws Exception{
        // 전체 지우고 카운트
        categoryDao.deleteAll();
        assertTrue(categoryDao.count()==0);

        // 1개 추가하고 삭제. 개수 카운트
        CategoryDto categoryDto = new CategoryDto("cate_num", "cate_name", 1,
                'Y', "cur_layr_num", "whol_layr_name");
        assertTrue(categoryDao.insert(categoryDto)==1);
        assertTrue(categoryDao.deleteAll()==1);
        assertTrue(categoryDao.count()==0);

        // 100개 추가하고 삭제. 개수 카운트
        for (int i = 1; i <= 100; i++) {
            categoryDto = new CategoryDto("cate_num"+i, "cate_name" +i, i,
                    'Y', "cur_layr_num" + i, "whol_layr_name" + i);
            assertTrue(categoryDao.insert(categoryDto) == 1);
        }
        assertTrue(categoryDao.deleteAll()==100);
        assertTrue(categoryDao.count()==0);
    }

    @Test
    public void deleteTest() throws Exception{
        // 전체 지우고 카운트
        categoryDao.deleteAll();
        assertTrue(categoryDao.count()==0);

        // 하나 넣고 삭제
        CategoryDto categoryDto = new CategoryDto("cate_num", "cate_name", 1,
                'Y', "cur_layr_num", "whol_layr_name");
        assertTrue(categoryDao.insert(categoryDto)==1);
        assertTrue(categoryDao.delete(categoryDto.getCate_num())==1);
        assertTrue(categoryDao.count()==0);

        // 전체 지우고 카운트
        categoryDao.deleteAll();
        assertTrue(categoryDao.count()==0);

        // 잘못된 cate_num주고 삭제
        categoryDto = new CategoryDto("cate_num", "cate_name", 1,
                'Y', "cur_layr_num", "whol_layr_name");
        assertTrue(categoryDao.insert(categoryDto)==1);
        assertTrue(categoryDao.delete(categoryDto.getCate_num() + 111)==0); //삭제 실패
        assertTrue(categoryDao.count()==1);
    }

    @Test
    public void insertTest() throws Exception{
        // 전체 지우고 카운트
        categoryDao.deleteAll();
        assertTrue(categoryDao.count()==0);

        // 1개 추가
        CategoryDto categoryDto = new CategoryDto("cate_num", "cate_name", 1,
                'Y', "cur_layr_num", "whol_layr_name");
        assertTrue(categoryDao.insert(categoryDto) == 1);

        // pk 중복값 추가 불가
        try {
            assertTrue(categoryDao.insert(categoryDto) == 0);
            assertTrue(categoryDao.insert(categoryDto) == 0);
            assertTrue(categoryDao.insert(categoryDto) == 0);
        }catch(Exception e){}

        // 전체 지우고 카운트
        categoryDao.deleteAll();
        assertTrue(categoryDao.count()==0);

        // 100개 추가하고 개수 카운트
        for (int i = 1; i <= 100; i++) {
            categoryDto = new CategoryDto("cate_num"+i, "cate_name" +i, i,
                    'Y', "cur_layr_num" + i, "whol_layr_name" + i);
            assertTrue(categoryDao.insert(categoryDto) == 1);
        }
        assertTrue(categoryDao.count()==100);
    }

    @Test
    public void selectAllTest() throws Exception{
        // 전체 지우고 카운트
        categoryDao.deleteAll();

        // 데이터 없는 상태에서 전체 조회 후 카운트
        List<CategoryDto> cateList = categoryDao.selectAll();
        assertTrue(categoryDao.count() == 0);

        // 한개 넣고 전체 조회 후 카운트
        CategoryDto categoryDto = new CategoryDto("cate_num", "cate_name", 1,
                'Y', "cur_layr_num", "whol_layr_name");
        assertTrue(categoryDao.insert(categoryDto) == 1);
        System.out.println("cate:" + cateList.size());
        cateList = categoryDao.selectAll();
        assertTrue(cateList.size() == 1);

        // 전체 지우고 카운트
        categoryDao.deleteAll();
        assertTrue(categoryDao.count()==0);

        // 100개 추가하고 전체조회 후 카운트
        for (int i = 1; i <= 100; i++) {
            categoryDto = new CategoryDto("cate_num"+i, "cate_name" +i, i,
                    'Y', "cur_layr_num" + i, "whol_layr_name" + i);
            assertTrue(categoryDao.insert(categoryDto) == 1);
        }
        cateList = categoryDao.selectAll();
        assertTrue(cateList.size() == 100);
    }

    @Test
    public void selectTest() throws Exception{
        // 전체 지우고 카운트
        categoryDao.deleteAll();
        assertTrue(categoryDao.count()==0);

        // 1개 추가
        CategoryDto categoryDto = new CategoryDto("cate_num", "cate_name", 1,
                'Y', "cur_layr_num", "whol_layr_name");
        assertTrue(categoryDao.insert(categoryDto) == 1);

        //cate_num 가져오기
        String cate_num = categoryDao.selectAll().get(0).getCate_num();

        // 다른 객체 생성해서 넣고 같은지 비교
        CategoryDto categoryDto2 = categoryDao.select(cate_num);
        System.out.println("publisherDto:" + categoryDto +"\n" + "publisherDto2:" + categoryDto2);
        assertTrue(categoryDto.equals(categoryDto2));
    }

    @Test
    public void update() throws Exception{
        // 전체 지우고 카운트
        categoryDao.deleteAll();
        assertTrue(categoryDao.count()==0);

        // 1개 추가
        CategoryDto categoryDto = new CategoryDto("cate_num", "cate_name", 1,
                'Y', "cur_layr_num", "whol_layr_name");
        assertTrue(categoryDao.insert(categoryDto) == 1);

        //cate_num 가져오기
        String cate_num = categoryDao.selectAll().get(0).getCate_num();
        System.out.println("isbn: " + cate_num);

        // categoryDto 객체 속성값 바꾸고 업데이트
        categoryDto.setCate_num(cate_num);
        categoryDto.setCate_name("내가 바꾼 출판사명");
        assertTrue(categoryDao.update(categoryDto) == 1);

        // 업데이트한 객체와 같은지 체크
        CategoryDto categoryDto2 = categoryDao.select(cate_num);
        assertTrue(categoryDto2.equals(categoryDto));
    }
}