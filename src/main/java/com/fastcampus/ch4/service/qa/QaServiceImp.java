package com.fastcampus.ch4.service.qa;


import com.fastcampus.ch4.dao.qa.QaDao;
import com.fastcampus.ch4.dao.qa.QaDaoImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QaServiceImp {

    private final QaDao dao;

    @Autowired
    public QaServiceImp(QaDao dao) {
        this.dao = dao;
    }


}
