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


//    // 동의한 약관의 유효성을 검사
//    public void validateTermAgreements(List<TermAgreeDto> termAgreeDtos) {
//        // 약관 정보를 조회
//        List<TermDto> terms = getAllTerms();
//        Map<Integer, TermDto> termMap = terms.stream().collect(Collectors.toMap(TermDto::getTermId, Function.identity()));
//
//        // 동의한 약관 ID 리스트 생성
//        List<Integer> agreedTermIds = termAgreeDtos.stream()
//                .filter(termAgree -> "Y".equals(termAgree.getTermAgree()))
//                .map(TermAgreeDto::getTermId)
//                .collect(Collectors.toList());
//
//        // 필수 약관에 대한 동의 여부 확인
//        for (TermDto termDto : termMap.values()) {
//            if ("Y".equals(termDto.getRequired()) && !agreedTermIds.contains(termDto.getTermId())) {
//                throw new IllegalArgumentException("필수 약관에 동의해야 합니다: 약관 ID " + termDto.getTermId());
//            }
//        }
//    }
//
//    // 약관 동의 정보 등록
//    public void saveTermAgreements(String memberId, List<TermAgreeDto> termAgreeDtos) {
//        LocalDateTime now = LocalDateTime.now();
//        for (TermAgreeDto termAgreeDto : termAgreeDtos) {
//            termAgreeDto.setId(memberId);
//            termAgreeDto.setRegDate(now);
//            termAgreeDto.setRegId(memberId);
//            termAgreeDto.setUpDate(now);
//            termAgreeDto.setUpId(memberId);
//        }
//        // DB에 저장하는 로직을 구현
//        termDao.insertTermAgreements(termAgreeDtos);
//
//    }

    @Override
    public List<Integer> getAllTermIds() {
        // 모든 약관의 ID만 가져오는 메서드
        return termDao.getAllTermIds();
        // 데이터베이스나 저장소에서 약관 ID 리스트를 가져오는 로직
        // 예를 들어, [1, 2, 3, 4]와 같은 리스트를 반환
    }

    @Override
    public List<TermAgreeDto> getTermAgreements(String memberId) {
        return termAgreeDao.getTermAgreements(memberId);
    }
}
