package com.fastcampus.ch4.dao.admin;

import com.fastcampus.ch4.dto.admin.AdminDto;

import java.util.List;

public interface AdminDao {
    int count();

    AdminDto select(String id);

    List<AdminDto> selectAll();

    int delete(String id);

    int deleteAll();

    int insert(AdminDto adminDto);
}
