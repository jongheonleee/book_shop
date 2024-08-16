package com.fastcampus.ch4.service.faq;

import com.fastcampus.ch4.dao.faq.FaqCateDao;
import com.fastcampus.ch4.dto.faq.FaqCateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class FaqCateServiceImpl implements FaqCateService {

    @Autowired
    FaqCateDao faqCateDao;

    public List<FaqCateDto> readAll() {
        return faqCateDao.selectAll();
    }

    public List<FaqCateDto> readChildByParent(String cateCode) {
        return faqCateDao.selectChildByParent(cateCode);
    }

    public List<FaqCateDto> readChildAndParent(String cateCode) {
        return faqCateDao.selectChildAndParent(cateCode);
    }

    public List<FaqCateDto> readMainCate() {
        return faqCateDao.selectMainCate();
    }

    public List<FaqCateDto> readSubCate() {
        return faqCateDao.selectSubCate();
    }

    public int create(FaqCateDto dto) {
        return faqCateDao.insert(dto);
    }
}
