package com.fastcampus.ch4.controller.faq;

import com.fastcampus.ch4.domain.SearchCondition;
import com.fastcampus.ch4.dto.faq.FaqCateDto;
import com.fastcampus.ch4.dto.faq.FaqDto;
import com.fastcampus.ch4.exception.UnauthorizedAccessException;
import com.fastcampus.ch4.service.faq.FaqCateService;
import com.fastcampus.ch4.service.faq.FaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("cscenter/faq")
public class FaqController {

    @Autowired
    private FaqService faqService;

    @Autowired
    private FaqCateService faqCateService;

    // 에러 종류별로 다 따로 처리르 해줘야 하나???
    @ExceptionHandler(Exception.class)
    public String catcher(Exception ex, Model model) {
        model.addAttribute("errorMsg", ex.getMessage());
        return "faq/error";
    }

    // 아! 글로벌 ExceptionHandler 만들어야겠다!!!

    /*
    @GetMapping("/list")
    public String faq(Model model, HttpServletRequest request){
        // sesseion에서 id 얻어온 후 관리자인지 확인하여 관리자여부 모델에 담기

        // faq 카테고리 리스트 model에 담아 넘겨주기
        List<FaqCateDto> faqCateList = faqCateService.readMainCate();
        model.addAttribute("faqCateList", faqCateList);

        List<FaqDto> list = faqService.readDisplay();
        model.addAttribute("faqList", list);
        int totalCnt = list.size();
        model.addAttribute("totalCnt", totalCnt);

        // 목록 페이지 반환
        return "faq/list";
    }
     */

    @GetMapping("/list/{cateCode}")
    public String faqList(@PathVariable String cateCode, Model model, HttpServletRequest request) {
        // session 객체 얻어서 관리자 여부 model에 담아 넘겨주기
        HttpSession session = request.getSession(false);
        model.addAttribute("role", session == null ? "" : session.getAttribute("role"));

        /*
        // role == admin인 경우 test 하려고 만듦
        HttpSession session = request.getSession();
        String id = "110111";
        String role = "admin";
        session.setAttribute("id", id);
        session.setAttribute("role", role);

        model.addAttribute("role", session == null ? "" : session.getAttribute("role"));
        */

        // 로그인이 안 되어있으면 로그인 페이지로 넘어가도록 해야겠다!! - 추가하기!

        // faq 전체 카테고리 리스트 model에 담아 넘겨주기
        model.addAttribute("faqCateTotalList", faqCateService.readAll());

        // faq 카테고리 리스트 model에 담아 넘겨주기
        model.addAttribute("faqCateList", faqCateService.readChildAndParent(cateCode));

        // 게시글 가져와서 목록 넘기기 (관리자면 노출여부 N인 글도 조회해서 넘겨줘야 하는데....ㅠㅠ)
        List<FaqDto> list = faqService.readByCatgCode(cateCode);
        model.addAttribute("faqList", list);
        int totalCnt = list.size();
        model.addAttribute("totalCnt", totalCnt);

        model.addAttribute("cateCode", cateCode);

        return "faq/list";
    }

    // 유효성 검증으로 검색어 길이 제한 해보기!!!
    @GetMapping("/search")
    public String search(Model model, SearchCondition sc, HttpServletRequest request) {
        List<FaqDto> faqList = faqService.searchByKeyword(sc);
        model.addAttribute("faqList", faqList);
        model.addAttribute("totalCnt", faqList.size());

        // faq 전체 카테고리 리스트 model에 담아 넘겨주기
        List<FaqCateDto> faqCateTotalList = faqCateService.readAll();
        model.addAttribute("faqCateTotalList", faqCateTotalList);

        return "faq/list";
    }

    // 게시글 읽기창
    @GetMapping("/detail/{faq_seq}")
    public String read(@PathVariable Integer faq_seq, Model model, HttpServletRequest request) {
        // session 객체 얻어서 관리자 여부 model에 담아 넘겨주기
        HttpSession session = request.getSession(false);
        model.addAttribute("role", session == null ? "" : session.getAttribute("role"));

        // 게시글 번호로 faqDto 얻어와서 모델에 담기
        FaqDto faqDto = faqService.read(faq_seq);
        model.addAttribute("faqDto", faqDto);

        // 중분류 코드 갖고 와서 넣어주자!
        List<FaqCateDto> faqCateList = faqCateService.readSubCate();
        model.addAttribute("faqCateList", faqCateList);

        return "faq/faq";
    }

    // 게시글 작성 - 관리자만 작성 가능
    @GetMapping("/write")
    public String write(Model model, HttpServletRequest request) {
        // 세션을 얻어서 세션에 저장된 role을 확인한다.
        HttpSession session = request.getSession(false);

        // session이 null인지 확인 + session의 role이 admin인지 확인 후 admin이 아니면 글을 쓸 권한이 없다고 한다.
        if (session == null || !"admin".equals(session.getAttribute("role"))) {
            throw new UnauthorizedAccessException("글 작성 권한이 없습니다.");
        }

        // 중분류 코드 갖고 와서 넣어주자!
        List<FaqCateDto> faqCateList = faqCateService.readSubCate();
        model.addAttribute("faqCateList", faqCateList);

        model.addAttribute("mode", "new");

        // admin이면 글쓰기 창으로 보내준다.
        return "faq/faq";
    }

    // 게시글 작성
    @PostMapping("/write")
    public String write(FaqDto faqDto, Model model, HttpServletRequest request) {
        // 세션에 저장된 id 얻어서 faqDto의 reg_id에 저장
        HttpSession session = request.getSession();

        // 관리자가 아닌 경우 예외 발생하는지 확인하는 코드
//        session.setAttribute("id", "");
//        session.setAttribute("role", "");

        faqDto.setReg_id((String)session.getAttribute("id"));

        // faqDto를 등록
        faqService.write(faqDto);

        // 중분류 코드 갖고 와서 넣어주자!
        List<FaqCateDto> faqCateList = faqCateService.readSubCate();
        model.addAttribute("faqCateList", faqCateList);

        return "redirect:list/00";
        // 만약 작성 후 글 목록 말고 내가 작성한 글 읽기 페이지로 가고 싶으면... faq_seq를 넘겨줘야 할텐데 어떻게..?
    }

    // 게시글 수정
    @PostMapping("/modify/{faq_seq}")
    public String modify(@PathVariable Integer faq_seq, FaqDto faqDto, Model model, HttpServletRequest request) {
        // session 객체 얻어서 관리자 여부 model에 담아 넘겨주기
        HttpSession session = request.getSession(false);
        model.addAttribute("role", session == null ? "" : session.getAttribute("role"));

        faqDto.setUp_id(session.getAttribute("id").toString());
        faqService.modify(faqDto);

        // 중분류 코드 갖고 와서 넣어주자!
        List<FaqCateDto> faqCateList = faqCateService.readSubCate();
        model.addAttribute("faqCateList", faqCateService.readSubCate());

        return "redirect:/cscenter/faq/list/00";
    }

    // 게시글 삭제 - 권한 있는 관리자만 삭제 가능

}
