package com.fastcampus.ch4.controller.qa;


import static com.fastcampus.ch4.code.error.qa.QaErrorCode.DUPLICATED_KEY;
import static com.fastcampus.ch4.code.error.qa.QaErrorCode.INVALID_VALUE_INPUT;

import com.fastcampus.ch4.domain.qa.PageHandler;
import com.fastcampus.ch4.dto.global.CodeDto;
import com.fastcampus.ch4.dto.qa.QaDto;
import com.fastcampus.ch4.domain.qa.SearchCondition;

import com.fastcampus.ch4.dto.qa.ReplyDto;
import com.fastcampus.ch4.service.qa.QaService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Validated // 유효성 검증
@Controller
public class QaController {

    private final QaService qaService; // 이름 명확히

    @Autowired
    public QaController(QaService qaService) {
        this.qaService = qaService;
    }

    /**
     *  1차 기능 요구 사항 정리
     *  - (1) 유저 관련 문의글 카운팅
     *  - (2) 유저 관련 문의글 조회
     *  - (3) 유저 관련 문의글 중에서 특정 키워드로 검색(기간, 제목, 기간&제목)
     *  - (4) 특정 문의글 상세 페이지 이동
     *  - (5) 유저 문의글 작성 페이지 이동
     *  - (6) 유저 문의글 작성
     *  - (7) 유저 문의글 삭제
     *  - (8) 유저 문의글 수정
     **/

    // 예외 처리(런타임) -> DuplicateKeyException, UncategorizedSQLException, DataIntegrityViolationException
    @ExceptionHandler({DataIntegrityViolationException.class, DataIntegrityViolationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleInvalidInput() {
        return ResponseEntity.status(INVALID_VALUE_INPUT.getHttpStatus()).body(INVALID_VALUE_INPUT.getMessage());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ResponseEntity<String> handleConflict() {
        return ResponseEntity.status(DUPLICATED_KEY.getHttpStatus()).body(DUPLICATED_KEY.getMessage());
    }


    // (2) 유저 관련 문의글 조회
    @GetMapping("/qa/list")
    public String getList(HttpServletRequest request, Model model, SearchCondition sc) {
        // 로그인 여부 확인, 로그인 x -> 로그인 폼으로 이동
        if (!isLogin(request)) return "redirect:/loginForm";

        // 조회 하는 코드

        // 필요 정보 조회 - 1. 유저 문의글, 2. 유저 문의글 수, 3. 페이징
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        userId = "user1";
        List<QaDto> selected = qaService.read(userId, sc);
        int totalCnt = count(userId);
        PageHandler ph = new PageHandler(totalCnt, sc);

        // 정보 저장
        model.addAttribute("selected", selected);
        model.addAttribute("totalCnt", totalCnt);
        model.addAttribute("ph", ph);

        // 뷰 반환
        return "/qa/list";
    }

    // (3) 유저 관련 문의글 중에서 특정 키워드로 검색(기간, 제목, 기간&제목)
    @GetMapping("/qa/search")
    public String getSearch(HttpServletRequest request, Model model, SearchCondition sc) {
        // 로그인 여부 확인, 로그인 x -> 로그인 폼으로 이동
        if (!isLogin(request)) return "redirect:/loginForm"; // 이 부분도 중복 제거하기, 스프링 시큐리티(필터), 세션에서 처리

        // 필요 정보 조회 - 1. 검색 문의글, 2. 검색 문의글 수, 3. 페이징
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        userId = "user1";
        List<QaDto> selected = qaService.readBySearchCondition(userId, sc); // 여기 최대 길이 10임

        int totalCnt = qaService.count(userId, sc); // 이 부분 고쳐야함 count(userId, sc)
        PageHandler ph = new PageHandler(totalCnt, sc);

        // 정보 저장
        model.addAttribute("selected", selected);
        model.addAttribute("totalCnt", totalCnt);
        model.addAttribute("ph", ph);

        // 뷰 반환 -> /qa/search로 변경
        return "/qa/list";
    }

    // (4) 특정 문의글 상세 페이지 이동
    @GetMapping("/qa/{qaNum}")
    public String getDetail(HttpServletRequest request, Model model, @PathVariable int qaNum) {
        // 로그인 여부 확인, 로그인 x -> 로그인 폼으로 이동
        if (!isLogin(request)) return "redirect:/loginForm";

        // 필요 정보 조회 - 1. 해당 문의글, 2. 답변글(추후에 개발)
        QaDto qaDto = qaService.readDetail(qaNum);

        // AnswerList 조회
        ReplyDto replyDto = qaService.readReply(qaNum);

        // 정보 저장
        model.addAttribute("qa", qaDto);
        model.addAttribute("reply", replyDto);

        // 뷰 반환
        return "/qa/detail";
    }

    // (5) 유저 문의글 작성 페이지 이동
    @GetMapping("/qa/form")
    public String getQaForm(HttpServletRequest request, Model model) {
        // 로그인 여부 확인, 로그인 x -> 로그인 폼으로 이동
        if (!isLogin(request)) return "redirect:/loginForm";

        // 뷰 반환
        return "/qa/form";
    }


    // (6) 유저 문의글 작성
    @PostMapping("/qa/form")
    public String sendQaForm(HttpServletRequest request, QaDto qaDto, SearchCondition sc, Model model) {
        // 로그인 여부 확인, 로그인 x -> 로그인 폼으로 이동
        if (!isLogin(request)) return "redirect:/loginForm";

        // 등록 작업 시행
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        userId = "user1";

        // 등록 실패 시 에러 메시지 전달
        if (!qaService.write(userId, qaDto)) {
            String errorMsg = "서버 내부 오류가 발생했거나, 동일한 제목의 문의글이 존재합니다.";
            model.addAttribute("errorMsg", errorMsg);
            return "/qa/error";
        }

        // 등록 성공하면 문의글 리스트로 이동
        return "redirect:/qa/list";
    }

    // (7) 유저 문의글 삭제
    @GetMapping("/qa/delete")
    public String removeQa(HttpServletRequest request, @RequestParam(name = "qa_num") int qaNum, QaDto dto, Model model) {
        // 로그인 여부 확인, 로그인 x -> 로그인 폼으로 이동
        if (!isLogin(request)) return "redirect:/loginForm";

        // 삭제 작업 시행
        dto.setQa_num(qaNum);
        if (!qaService.remove(dto)) {
            model.addAttribute("msg", "문의글 삭제에 실패했습니다.");
            return "/qa/error";
        }

        // 삭제 성고하면 문의글 리스트로 이동
        return "redirect:/qa/list";
    }

    // (8) 유저 문의글 수정 페이지
    @GetMapping("/qa/modify")
    public String getUpdate(HttpServletRequest request, @RequestParam(name = "qa_num") int qaNum, Model model, QaDto dto) {
        // 로그인 여부 확인
        if (!isLogin(request)) return "redirect:/loginForm";

        // 기존 문의글 저장
        QaDto qa = qaService.readDetail(qaNum);
        model.addAttribute("qa", qa);

        // 문의글 수정 페이지 반환
        return "/qa/modify";
    }

    // (8) 유저 문의글 수정
    @PostMapping("/qa/modify")
    public String updateQa(HttpServletRequest request, QaDto dto,SearchCondition sc, Model model) {
        // 로그인 여부 확인, 로그인 x -> 로그인 폼으로 이동
        if (!isLogin(request)) return "redirect:/loginForm";

        // 수정 작업 시행
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        userId = "user1";

        if (!qaService.modify(userId, dto, sc)) {
            model.addAttribute("msg", "문의글 수정에 실패했습니다");
            return "/qa/error";
        }

        // 수정 성공하면 문의글 리스트로 이동
        return "redirect:/qa/list";
    }

    @GetMapping("/qa/list/{qa_stat_code}")
    public String getListByQaState(@PathVariable String qa_stat_code, HttpServletRequest request, Model model, SearchCondition sc) {
        // 로그인 여부 확인
        if (!isLogin(request)) return "redirect:/loginForm";

        // 세션에 저장된 유저 정보 조회
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        userId = "user1";


        // 상태를 기반으로 관련 문의글 카운트 및 조회
        int totalCnt = qaService.countByState(userId, qa_stat_code);
        List<QaDto> selected = qaService.readByState(userId, qa_stat_code, sc);

        // 페이지 핸들러 생성
        PageHandler ph = new PageHandler(totalCnt, sc);

        // 웹 페이지에 보여줘야할 정보 저장
        model.addAttribute("selected", selected);
        model.addAttribute("totalCnt", totalCnt);
        model.addAttribute("ph", ph);

        // 뷰 반환
        return "/qa/list";
    }


    // 공통 로직 : 로그인 여부 확인
    private boolean isLogin(HttpServletRequest request) {
        // 세션에서 로그인 정보 조회
        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("userId");
        String userId = "user1";

        // 정보 서로 일치하는지 비교
        boolean check = userId.equals(sessionId);
        return true;
    }

    @PostMapping("/admin/qa/reply")
    public String addReply(HttpServletRequest request, ReplyDto dto) {
        // 로그인 여부 확인
        if (!isLogin(request)) return "redirect:/loginForm";

        // 관리자 여부 확인


        // 답변 등록
        if (!qaService.addReply(dto)) {
            return "/qa/error";
        }

        // 답변 등록 성공하면 문의글 상세 페이지로 이동
        return "redirect:/qa/" + dto.getQa_num();
    }

    // (1) 유저 관련 문의글 카운팅
    private int count(String userId) {
        return qaService.count(userId);
    }


    @ModelAttribute("states")
    private List<CodeDto> readState() {
        return qaService.readAllCategory("01");
    }

    @ModelAttribute("categories")
    private List<CodeDto> readCategory() {
        return qaService.readAllCategory("02");
    }

}
