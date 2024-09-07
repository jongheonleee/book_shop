package com.fastcampus.ch4.dao.item;

import com.fastcampus.ch4.dto.item.CategoryDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao {
    @Autowired
    private SqlSession session;

    // PublisherMapper.xml에 작성한 별명과 같아야됨.
    private static String namespace = "com.fastcampus.ch4.dao.item.CategoryMapper.";


    @Override
    public int count() { //전체 개수 반환
        return session.selectOne(namespace + "count");
    }

    @Override
    public int deleteAll() { // 전체 삭제. 성공 1, 실패 0
        return session.delete(namespace + "deleteAll");
    }

    @Override
    public int delete(String cate_num) { // isbn과 pub_num일치하면 삭제. 성공 1, 실패 0
        return session.delete(namespace + "delete", cate_num);
    }

    @Override
    public int insert(CategoryDto dto) { //CategoryDto 테이블에 insert
        return session.insert(namespace + "insert", dto);
    }

    @Override
    public List<CategoryDto> selectAll() { // 전체 테이블 조회(등록일 내림차순)
        return session.selectList(namespace + "selectAll");
    }

    @Override
    public CategoryDto select(String cate_num) { // cate_num 일치하는 행 들고오기
        return session.selectOne(namespace + "select", cate_num);
    }

    @Override
    public int update(CategoryDto dto) { // CategoryDto정보로 cate_num이 일치하는 행 update
        return session.update(namespace + "update", dto);
    }
}
