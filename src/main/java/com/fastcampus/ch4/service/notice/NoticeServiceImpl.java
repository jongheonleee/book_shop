package com.fastcampus.ch4.service.notice;

import com.fastcampus.ch4.dao.notice.NoticeDao;
import com.fastcampus.ch4.dto.notice.NoticeDto;
import com.fastcampus.ch4.exception.notice.AutoIncrementOverflowException;
import com.fastcampus.ch4.exception.notice.DataProcessingException;
import com.fastcampus.ch4.exception.notice.InvalidDataException;
import com.fastcampus.ch4.exception.notice.InvalidPostException;
import com.fastcampus.ch4.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.fastcampus.ch4.domain.notice.ErrorMessage.*;

@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeDao noticeDao;
    private final NoticeCateService noticeCateService;
    private final AdminService adminService;

    @Autowired
    public NoticeServiceImpl(NoticeDao noticeDao, NoticeCateService noticeCateService, AdminService adminService) {
        this.noticeDao = noticeDao;
        this.noticeCateService = noticeCateService;
        this.adminService = adminService;
    }

    // count : 게시글 전체 건수
    @Override
    public int count() {
        return noticeDao.count();
    }

    // countDsply : 노출되는 게시글 건수
    @Override
    public int countDsply() {
        return noticeDao.countDsply();
    }

    // write
    @Override
    public void write(NoticeDto noticeDto) {
        // 글 작성자가 관리자인지 확인 -> AOP 적용
        // 관리자가 아니면 글 작성 실패 -> 글 작성 권한 없음, 예외 발생시켜 관리자 계정 로그인 유도

        // 카테고리 코드로 해당 코드의 id 조회
        // 코드로 조회 시 예외 발생하면 여기서 한 차례 처리를 해줘야 하나? -> 일단 여기서 처리할 건 없는 것 같으니 넘기기
        Integer ntcCateId = noticeCateService.getIdByCode(noticeDto.getNtc_cate_code());
        // 조회한 카테고리 id를 dto에 입력
        noticeDto.setNtc_cate_id(ntcCateId);

        // dsply_chk가 "Y" 또는 "N"이 맞는지 확인 -> 아니면 예외 발생? 굳이? 그냥 "Y"로 하자. 글을 보여주고 쓴 거일텐데...
        // 화면에는 체크박스일텐데 잘못된 값이 넘어왔다면 사용자 잘못이 아니라 개발자나 프로그램 잘못이니까 기본으로 값 넣어주도록 하는 게 나을 듯
        // 근데 프로그램 문제라면 로그를 남겨야 하는 거 아닌가??
        if (!"Y".equalsIgnoreCase(noticeDto.getDsply_chk())) {
            noticeDto.setDsply_chk("N");
        }

        // pin_top_chk가 "Y"이면 pin_top_end_date가 null이면 안 되고 현재 날짜와 같거나 미래여야 한다.
        // pin_top_chk가 "Y" && pin_top_end_date == null || pin_top_chk가 "Y" && pin_top_end_date가 현재날짜보다 이전이면 예외 발생
        if ("Y".equalsIgnoreCase(noticeDto.getPin_top_chk())) {
            String pinTopEndDate = noticeDto.getPin_top_end_date();
            if (pinTopEndDate == null) {
                throw new InvalidDataException(INVALID_DATA.getMsg());
            }

            LocalDate date = LocalDate.parse(pinTopEndDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if (date.isBefore(LocalDate.now())) {
                throw new InvalidDataException(INVALID_DATA.getMsg());
            }
        } else {
            // pin_top_chk가 "Y"가 아니면 "N"(default)으로 설정
            noticeDto.setPin_top_chk("N");
        }

        // noticeDto DB에 insert 시도
        // 입력 실패에 대한 처리
        // DuplicateKeyException 발생 -> auto increment 최대값 도달했을 때 발생 -> DB 관리자에게 알려야 함 (AutoIncrementOverflowException)
        // DataIntegrityViolationException 발생 -> 유효한 값 입력해달라고 요청 (InvalidDataException)
        // 이외에 발생하는 예외에 대해서 -> 일정 횟수정도 재시도 해보고 그래도 실패하면 예외 발생시켜 알리기 (DataProcessingException)
        // return 값이 0인 경우 -> 일정 횟수 재시도 -> 그래도 실패하면 예외 발생시켜 알리기 (DataProcessingException)
        try {
            int result = noticeDao.insert(noticeDto);
            if (result <= 0) {
                throw new DataProcessingException();
            }
        } catch (DuplicateKeyException e) {
            throw new AutoIncrementOverflowException(CONTACT_ADMIN.getMsg());
        } catch (DataIntegrityViolationException e) {
            throw new InvalidDataException(INVALID_DATA.getMsg());
        } catch (Exception e) {
            throw new DataProcessingException(TRY_AGAIN_LATER.getMsg());
        }
    }


    // read : 게시글 1건 읽기
    @Override
    public NoticeDto read(String userId, int ntc_seq) {
        // ntc_seq로 게시글 읽기
        NoticeDto noticeDto = noticeDao.select(ntc_seq);

        // 없는 게시글일 경우 예외 발생
        // 일반사용자일 경우 ntc_seq로 읽어온 글의 노출여부가 "N"이면 예외 발생 - "유효하지 않은 게시글입니다."
        if (noticeDto == null ||
                (!adminService.isAdmin(userId) && "N".equals(noticeDto.getDsply_chk()))) {
            throw new InvalidPostException(INVALID_POST.getMsg());
        }

        // 읽기 성공하면 조회수 1 증가 - 예외 발생하면 사용자 정의 예외로 감싸서 예외 던지기
        try {
            noticeDao.increaseViewCnt(ntc_seq);
        } catch (DataIntegrityViolationException e) {
            throw new AutoIncrementOverflowException(CONTACT_ADMIN.getMsg());
        }

        // 이외에는 읽은 게시글 반환
        return noticeDto;
    }


    // getList : 게시글 전체 목록 - admin만? 일단 필요 없을 것 같아서 보류


    // getPage
    // admin이면 노출여부 상관 없이 모두 선택, admin 아니면 노출여부 Y인 글만 선택
    @Override
    public List<NoticeDto> getPage(String userId, Map map) {
        // cate_code를 cate_id로 변환
        String cateCode = map.get("cateCode").toString();
        if (!"ALL".equals(cateCode)) {
            Integer ntcCateId = noticeCateService.getIdByCode(cateCode);
            map.put("ntcCateId", ntcCateId);
        }

        // admin이면 노출여부 상관없이 모두 선택 selectAll
        if (adminService.isAdmin(userId)) {
            return noticeDao.selectPage(map);
        }

        // admin 아니면 노출여부 Y인 글만 선택 selectDsply
        return noticeDao.selectPageDsply(map);
    }


    // modify : 게시글 수정
    @Override
    public void modify(NoticeDto noticeDto) {
        // 관리자가 아니면 권한 없음 예외 발생

    }


    // remove : 게시글 1건 삭제

    // removeAll : 게시글 전체 삭제
    @Override
    public int removeAll(String userId) {
        // user가 관리자인지 확인 -> 아니면 예외 발생 -> AOP 적용
        // 맞으면 삭제 진행
        return noticeDao.deleteAll();
    }

    @Override
    public AdminService getAdminService() {
        return adminService;
    }
}