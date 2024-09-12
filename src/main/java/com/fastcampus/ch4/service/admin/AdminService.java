package com.fastcampus.ch4.service.admin;

import com.fastcampus.ch4.dto.admin.AdminDto;

import java.util.List;

public interface AdminService {
    int count();

    int save(AdminDto adminDto);

    AdminDto read(String id);

    List<AdminDto> readAll();

    boolean isAdmin(String id);

    int removeAll();
}