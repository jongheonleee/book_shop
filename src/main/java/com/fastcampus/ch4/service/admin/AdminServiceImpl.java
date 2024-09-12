package com.fastcampus.ch4.service.admin;

import com.fastcampus.ch4.dao.admin.AdminDao;
import com.fastcampus.ch4.dto.admin.AdminDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminDao adminDao;

    @Autowired
    public AdminServiceImpl(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    @Override
    public int count() {
        return adminDao.count();
    }

    @Override
    public int save(AdminDto adminDto) {
        return adminDao.insert(adminDto);
    }

    @Override
    public AdminDto read(String id) {
        return adminDao.select(id);
    }

    @Override
    public List<AdminDto> readAll() {
        return adminDao.selectAll();
    }

    // id 기준으로 조회해서 사용자가 admin인지 아닌지 확인하는 메서드
    @Override
    public boolean isAdmin(String id) {
        return (id != null) && (null != adminDao.select(id));
    }

    @Override
    public int removeAll() {
        return adminDao.deleteAll();
    }
}