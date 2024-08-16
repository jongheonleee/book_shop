package com.fastcampus.ch4.dao.item;

import com.fastcampus.ch4.dto.item.CategoryDto;

import java.util.List;

public interface CategoryDao {
    int count() throws Exception;

    int deleteAll();

    int delete(String cate_num) throws Exception;

    int insert(CategoryDto dto) throws Exception;

    List<CategoryDto> selectAll() throws Exception;

    CategoryDto select(String cate_num) throws Exception;

    int update(CategoryDto dto) throws Exception;
}
