package com.fastcampus.ch4.service.member;

import com.fastcampus.ch4.dao.member.TermAgreeDao;
import com.fastcampus.ch4.dao.member.TermDao;
import com.fastcampus.ch4.dto.member.TermAgreeDto;
import com.fastcampus.ch4.dto.member.TermDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TermServiceImpl implements TermService {

    @Autowired
    private TermDao termDao;

    @Autowired
    private TermAgreeDao termAgreeDao;

    @Override
    public List<TermDto> getAllTerms() {
        return termDao.getAllTerms();
    }

    @Override
    public TermDto getTermById(int termId) {
        return termDao.getTermById(termId);
    }

    @Override
    public void validateTermAgreements(List<TermAgreeDto> termAgreeDtos) {
        // 약관 정보를 조회
        List<TermDto> terms = getAllTerms();
        Map<Integer, TermDto> termMap = terms.stream().collect(Collectors.toMap(TermDto::getTermId, Function.identity()));

        // 동의한 약관 ID 리스트 생성
        List<Integer> agreedTermIds = termAgreeDtos.stream()
                .filter(termAgree -> "Y".equals(termAgree.getTermAgree()))
                .map(TermAgreeDto::getTermId)
                .collect(Collectors.toList());

        // 필수 약관에 대한 동의 여부 확인
        for (TermDto termDto : termMap.values()) {
            if ("Y".equals(termDto.getRequired()) && !agreedTermIds.contains(termDto.getTermId())) {
                throw new IllegalArgumentException("필수 약관에 동의해야 합니다: 약관 ID " + termDto.getTermId());
            }
        }
    }

    @Override
    public void saveTermAgreements(List<TermAgreeDto> termAgreeDtos) {
        termAgreeDao.insertTermAgreements(termAgreeDtos);
    }


    @Override
    public List<Integer> getAllTermIds() {
        return termDao.getAllTermIds();
    }

    @Override
    public List<TermAgreeDto> getTermAgreements(String memberId) {
        return termAgreeDao.getTermAgreements(memberId);
    }
}
