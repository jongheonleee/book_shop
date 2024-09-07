package com.fastcampus.ch4.dao.item;

import com.fastcampus.ch4.dto.item.CategoryDto;

import java.util.List;

public interface CategoryDao {
    int count();

    int deleteAll();

    int delete(String cate_num);

    int insert(CategoryDto dto);

    List<CategoryDto> selectAll();

    CategoryDto select(String cate_num);

    int update(CategoryDto dto);
}
