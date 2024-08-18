package com.fastcampus.ch4.service.admin;

import com.fastcampus.ch4.dao.admin.AdminDao;
import com.fastcampus.ch4.dto.admin.AdminDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminDao adminDao;

    @Override
    public AdminDto read(String id) {
        return adminDao.select(id);
    }
}
