package com.fastcampus.ch4.service.notice;

import com.fastcampus.ch4.dao.notice.NoticeCateDao;
import com.fastcampus.ch4.dto.notice.NoticeCateDto;
import com.fastcampus.ch4.exception.notice.CategoryCodeUnavailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeCateServiceImpl implements NoticeCateService {

    private final NoticeCateDao noticeCateDao;

    @Autowired
    public NoticeCateServiceImpl(NoticeCateDao noticeCateDao) {
        this.noticeCateDao = noticeCateDao;
    }

    @Override
    public int count() {
        return noticeCateDao.count();
    }

    @Override
    public int save(NoticeCateDto noticeCateDto) {
        // try-catch로 예외 처리 필요!
        return noticeCateDao.insert(noticeCateDto);
    }

    // ntc_cate_code에 해당하는 ntc_cate_id 반환
    @Override
    public Integer getIdByCode(String code) {
        // code 유효성 검사 -> 뷰에서 잘못된 값이 넘어온 것이기 때문에 관리자에게 알려야 함!
        // 코드가 DB에 존재하지 않거나 use_chk가 "N"이면 예외 발생
        NoticeCateDto noticeCateDto = noticeCateDao.selectByCode(code);
        if (noticeCateDto == null || "N".equals(noticeCateDto.getUse_chk())) {
            throw new CategoryCodeUnavailableException("존재하지 않거나 비활성화된 카테고리 코드입니다.");
        }

        // 코드로 id 찾은 값 반환
//        return noticeCateDao.selectIdByCode(code);
        return noticeCateDto.getNtc_cate_id();
    }


    @Override
    public int removeAll() {
        return noticeCateDao.deleteAll();
    }
}
