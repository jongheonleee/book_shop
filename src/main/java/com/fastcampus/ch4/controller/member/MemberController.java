package com.fastcampus.ch4.controller.member;

import com.fastcampus.ch4.dto.member.MemberDto;
import com.fastcampus.ch4.dto.member.ShippingAddressDto;
import com.fastcampus.ch4.service.member.MemberManagementService;
import com.fastcampus.ch4.service.member.InfoChangeHistoryService;
import com.fastcampus.ch4.service.member.ShippingAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MemberController {

  @Autowired
  private MemberManagementService memberManagementService;

  @Autowired
  private InfoChangeHistoryService infoChangeHistoryService;

  @Autowired
  private ShippingAddressService shippingAddressService;

  // 프로필 수정 폼을 보여주는 메서드 (GET 요청)
  @GetMapping("/editProfile")
  public String showEditProfileForm(HttpServletRequest request, Model model) {
    // 현재 사용자 정보를 가져옴
    MemberDto currentUser = memberManagementService.getCurrentUser(request);
    if (currentUser == null) {
      model.addAttribute("error", "사용자 정보를 찾을 수 없습니다.");
      return "error"; // 사용자 정보를 찾을 수 없는 경우, 에러 페이지로 이동
    }

    // 현재 주소 정보를 가져옴
    ShippingAddressDto currentAddress = shippingAddressService.getAddressById(currentUser.getId());

    model.addAttribute("user", currentUser); // 현재 사용자 정보를 모델에 추가
    model.addAttribute("address", currentAddress); // 주소 정보를 모델에 추가

    return "member/editProfile"; // editProfile.jsp 페이지로 이동
  }

  // 프로필 업데이트를 처리하는 메서드 (POST 요청)
  @PostMapping("/updateProfile")
  public String updateProfile(MemberDto user, ShippingAddressDto addressDto, HttpServletRequest request, Model model) {
    // 세션에서 사용자 ID를 가져옴
    String userId = (String) request.getSession().getAttribute("id");
    if (userId == null) {
      model.addAttribute("error", "세션에서 사용자 정보를 찾을 수 없습니다.");
      return "error";
    }

    // 현재 사용자 정보 및 주소 정보 가져오기
    MemberDto currentUser = memberManagementService.getMemberById(userId);
    ShippingAddressDto currentAddress = shippingAddressService.getAddressById(userId);

    // 현재 비밀번호는 무조건 입력해야 함
    String currentPassword = request.getParameter("current-password");
    if (currentPassword == null || !currentUser.getPswd().equals(currentPassword)) {
      model.addAttribute("error", "현재 비밀번호가 일치하지 않거나 입력되지 않았습니다.");
      return "member/editProfile";
    }

    // 변경 플래그들
    boolean isProfileUpdated = false;
    boolean isPasswordUpdated = false;
    boolean isEmailUpdated = false;
    boolean isPhoneNumberUpdated = false;
    boolean isAddressUpdated = false;

    // 비밀번호 변경 처리 (새 비밀번호가 입력된 경우만)
    String newPassword = request.getParameter("new-password");
    String confirmNewPassword = request.getParameter("confirm-new-password");
    if (newPassword != null && !newPassword.isEmpty() && confirmNewPassword != null && !confirmNewPassword.isEmpty()) {
      if (newPassword.equals(confirmNewPassword)) {
        // 이전 비밀번호와 새 비밀번호 저장
        String beforePassword = currentUser.getPswd();
        currentUser.setPswd(newPassword); // 새 비밀번호로 업데이트
        isPasswordUpdated = true;

        // 비밀번호 변경 이력 저장 (값만 저장)
        infoChangeHistoryService.saveChangeHistory(
                "비밀번호",  // 변경 유형
                beforePassword, // 이전 비밀번호 저장
                newPassword,    // 새 비밀번호 저장
                currentUser
        );
      } else {
        model.addAttribute("error", "새 비밀번호가 일치하지 않습니다.");
        return "member/editProfile";
      }
    }

    // 이메일 변경 처리 (새 이메일이 입력된 경우만)
    String newEmail = request.getParameter("email");
    if (newEmail != null && !newEmail.isEmpty() && !newEmail.equals(currentUser.getEmail())) {
      infoChangeHistoryService.saveChangeHistory(
              "이메일",              // 변경 유형
              currentUser.getEmail(), // 이전 이메일
              newEmail,              // 새 이메일
              currentUser
      );
      currentUser.setEmail(newEmail);
      isEmailUpdated = true;
    }

    // 전화번호 변경 처리 (새 전화번호가 입력된 경우만)
    String newPhoneNumber = request.getParameter("phone");
    if (newPhoneNumber != null && !newPhoneNumber.isEmpty() && !newPhoneNumber.equals(currentUser.getPhnNumb())) {
      // 변경 이력 저장
      infoChangeHistoryService.saveChangeHistory(
              "전화번호",              // 변경 유형
              currentUser.getPhnNumb(), // 이전 전화번호
              newPhoneNumber,          // 새 전화번호
              currentUser
      );
      currentUser.setPhnNumb(newPhoneNumber);
      isPhoneNumberUpdated = true;
    }

    // 주소 업데이트 처리 (새 주소가 입력된 경우만)
    String newAddress = addressDto.getAddress();
    if (newAddress != null && !newAddress.isEmpty() && currentAddress != null && !newAddress.equals(currentAddress.getAddress())) {
      infoChangeHistoryService.saveChangeHistory(
              "주소",                 // 변경 유형
              currentAddress.getAddress(), // 이전 주소
              newAddress,                // 새 주소
              currentUser
      );
      currentAddress.setAddress(newAddress);
      shippingAddressService.updateAddress(currentAddress);
      isAddressUpdated = true;
    }

    // 변경 사항 적용
    isProfileUpdated = isPasswordUpdated || isEmailUpdated || isPhoneNumberUpdated || isAddressUpdated;

    if (isProfileUpdated) {
      memberManagementService.updateMember(currentUser); // 회원 정보 업데이트
    }

    // 결과 처리
    if (isProfileUpdated) {
      model.addAttribute("message", "프로필이 성공적으로 업데이트되었습니다!");
    } else {
      model.addAttribute("message", "변경 사항이 없습니다.");
    }

    return "member/result"; // 결과 페이지로 이동
  }

}
