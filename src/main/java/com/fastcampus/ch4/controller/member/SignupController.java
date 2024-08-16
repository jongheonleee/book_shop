package com.fastcampus.ch4.controller.member;

import com.fastcampus.ch4.dto.member.TermAgreeDto;
import com.fastcampus.ch4.dto.member.TermDto;
import com.fastcampus.ch4.service.member.TermService;
import com.fastcampus.ch4.dto.member.MemberDto;
import com.fastcampus.ch4.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.stream.Stream;

@Controller
@RequestMapping("/signup")
public class SignupController {

  @Autowired
  private MemberService memberService;

  @Autowired
  private TermService termService;

  // 회원가입 폼을 보여주는 메서드
  @RequestMapping
  public String showSignupForm(Model model) {
    // 약관 데이터를 조회하여 모델에 추가
    List<TermDto> terms = termService.getAllTerms();
    model.addAttribute("member", new MemberDto());
    model.addAttribute("terms", terms);
    return "member/signup"; // /WEB-INF/views/member/signup.jsp를 반환
  }

  @PostMapping
  public String processSignup(@ModelAttribute("member") MemberDto member,
                              @RequestParam(value = "requiredTermAgreedTermIds", required = false) List<Integer> requiredTerms,
                              @RequestParam(value = "optionalTermAgreedTermIds", required = false) List<Integer> optionalTerms,
                              Model model) {
    try {
//      // 약관 동의 정보 생성
//      List<TermAgreeDto> termAgreeDtos = createTermAgreeDtos(member.getId(), requiredTerms, optionalTerms);

      // 회원 가입 처리
      memberService.addMember(member);

      // 약관 동의 정보 저장
//      termService.saveTermAgreements(member.getId(), termAgreeDtos);

    } catch (IllegalArgumentException e) {
      // 예외가 발생한 경우 에러 메시지를 모델에 추가하고 폼으로 돌아감
      model.addAttribute("errorMessage", e.getMessage());
      return "member/signup";
    }
    return "redirect:/signup/signupSuccess";
  }

  @RequestMapping("/signupSuccess")
  public String showSignupSuccess(Model model) {
    return "member/signupSuccess"; // /WEB-INF/views/member/signupSuccess.jsp를 반환
  }

//    private List<TermAgreeDto> createTermAgreeDtos(String memberId, List<Integer> requiredTermIds, List<Integer> optionalTermIds) {
//      LocalDateTime now = LocalDateTime.now(); // 현재 시간
//
//      // null 체크 후 빈 리스트로 초기화
//      if (requiredTermIds == null) {
//        requiredTermIds = List.of();
//      }
//      if (optionalTermIds == null) {
//        optionalTermIds = List.of();
//      }
//
//      // 필수 약관 동의 정보를 생성
//      List<TermAgreeDto> requiredTermAgreeDtos = requiredTermIds.stream()
//              .map(termId -> new TermAgreeDto(
//                      memberId, // 회원 ID
//                      termId,   // 약관 ID
//                      "Y",      // 동의 여부 (Y/N)
//                      now,      // 등록일
//                      memberId, // 등록자 ID
//                      now,      // 수정일
//                      memberId  // 수정자 ID
//              ))
//              .collect(Collectors.toList());
//
//      // termService.getAllTerms()를 변수에 저장
//      List<TermDto> allTerms = termService.getAllTerms(); // final로 선언하지 않아도 무방
//
//      // 선택 약관 동의 정보를 생성
//      List<Integer> finalOptionalTermIds = optionalTermIds;
//      List<TermAgreeDto> optionalTermAgreeDtos = allTerms.stream()
//              .filter(term -> finalOptionalTermIds.contains(term.getTermId()))
//              .map(term -> new TermAgreeDto(
//                      memberId, // 회원 ID
//                      term.getTermId(),   // 약관 ID
//                      "Y",      // 동의 여부 (Y/N)
//                      now,      // 등록일
//                      memberId, // 등록자 ID
//                      now,      // 수정일
//                      memberId  // 수정자 ID
//              ))
//              .collect(Collectors.toList());
//
//      // 동의하지 않은 선택 약관도 포함
//      List<Integer> finalOptionalTermIds1 = optionalTermIds;
//      List<TermAgreeDto> nonAgreedOptionalTerms = allTerms.stream()
//              .filter(term -> !finalOptionalTermIds1.contains(term.getTermId()))
//              .map(term -> new TermAgreeDto(
//                      memberId, // 회원 ID
//                      term.getTermId(),   // 약관 ID
//                      "N",      // 동의 여부 (Y/N)
//                      now,      // 등록일
//                      memberId, // 등록자 ID
//                      now,      // 수정일
//                      memberId  // 수정자 ID
//              ))
//              .collect(Collectors.toList());
//
//      // 필수 약관과 선택 약관(동의한 것과 동의하지 않은 것)을 모두 포함한 리스트를 생성
//      List<TermAgreeDto> termAgreeDtos = Stream.concat(requiredTermAgreeDtos.stream(),
//                      Stream.concat(optionalTermAgreeDtos.stream(), nonAgreedOptionalTerms.stream()))
//              .collect(Collectors.toList());
//
//      return termAgreeDtos;
//    }

}