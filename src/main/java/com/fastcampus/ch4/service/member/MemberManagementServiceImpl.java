package com.fastcampus.ch4.service.member;

import com.fastcampus.ch4.dao.member.MemberDao;
import com.fastcampus.ch4.dao.member.InfoChangeHistoryDao;
import com.fastcampus.ch4.dto.member.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Service
public class MemberManagementServiceImpl implements MemberManagementService {

  private static final Logger logger = LoggerFactory.getLogger(MemberManagementServiceImpl.class);

  @Autowired
  private MemberDao memberDao;

  @Autowired
  private InfoChangeHistoryDao infoChangeHistoryDao;

  @Autowired
  private ShippingAddressService shippingAddressService;

  @Override
  @Transactional
  public void addMember(MemberDto member) {
    if (isIdDuplicate(member.getId())) {
      logger.warn("Attempt to register with an existing member ID: {}", member.getId());
      throw new RuntimeException("회원 ID가 이미 존재합니다.");
    }
    try {
      logger.info("Inserting member with ID: {}", member.getId());
      memberDao.insertMember(member);
      logger.info("Member with ID {} successfully inserted.", member.getId());
    } catch (Exception e) {
      logger.error("Error occurred during member registration for ID: {}", member.getId(), e);
      throw new RuntimeException("회원 가입 중 오류가 발생했습니다.", e);
    }
  }

  @Override
  public MemberDto getMemberById(String id) {
    logger.info("Fetching member information for ID: {}", id);
    MemberDto member = memberDao.selectMemberById(id);
    if (member == null) {
      logger.warn("No member found with ID: {}", id);
    }
    return member;
  }

  @Override
  public List<MemberDto> getAllMembers() {
    logger.info("Fetching all members");
    return memberDao.getAllMembers();
  }

  @Override
  public void updateMember(MemberDto member) {
    logger.info("Updating member information for ID: {}", member.getId());
    memberDao.updateMember(member);
    logger.info("Member with ID {} successfully updated.", member.getId());
  }

  @Override
  public void removeMember(String id) {
    logger.info("Removing member with ID: {}", id);
    memberDao.deleteMember(id);
    logger.info("Member with ID {} successfully removed.", id);
  }

  @Override
  public void removeAllMembers() {
    logger.info("Removing all members.");
    memberDao.deleteAll();
    logger.info("All members successfully removed.");
  }

  @Override
  public boolean isIdAvailable(String id) {
    logger.info("Checking if ID {} is available", id);
    return memberDao.isIdAvailable(id) == 0;
  }

  @Override
  public boolean isIdDuplicate(String id) {
    logger.info("Checking if member ID exists: {}", id);
    int count = memberDao.isIdAvailable(id);
    logger.info("ID check result for {}: {}", id, count > 0 ? "exists" : "does not exist");
    return count > 0;
  }

  @Override
  public MemberDto getCurrentUser(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session == null) {
      logger.error("No session found");
      throw new RuntimeException("세션이 존재하지 않습니다.");
    }

    String userId = (String) session.getAttribute("id");
    logger.info("Fetching current user with ID from session: {}", userId);
    if (userId != null) {
      return memberDao.selectMemberById(userId);
    }
    logger.error("No user found in session.");
    throw new RuntimeException("세션에서 사용자를 찾을 수 없습니다.");
  }

  @Override
  public boolean updateUserProfile(HttpServletRequest request, MemberDto updatedUser, ShippingAddressDto addressDto) {
    String userId = (String) request.getSession().getAttribute("id");
    if (userId == null) {
      throw new RuntimeException("세션에서 사용자 정보를 찾을 수 없습니다.");
    }

    MemberDto currentUser = getMemberById(userId);
    ShippingAddressDto currentAddress = shippingAddressService.getAddressById(userId);

    if (currentUser == null) {
      throw new RuntimeException("사용자를 찾을 수 없습니다.");
    }

    boolean isProfileUpdated = false;
    boolean isAddressUpdated = false;

    // 비밀번호 변경 처리
    String newPassword = updatedUser.getPswd();
    if (isNotEmpty(newPassword) && !newPassword.equals(currentUser.getPswd())) {
      logger.info("Updating password for user ID: {}", userId);
      updatePassword(userId, newPassword);
      saveChangeHistory("Password", currentUser.getPswd(), newPassword, currentUser);
      isProfileUpdated = true;
    }

    // 이메일 변경 처리
    String newEmail = updatedUser.getEmail();
    if (isNotEmpty(newEmail) && !newEmail.equals(currentUser.getEmail())) {
      logger.info("Updating email for user ID: {}", userId);
      updateEmail(userId, newEmail);
      saveChangeHistory("Email", currentUser.getEmail(), newEmail, currentUser);
      isProfileUpdated = true;
    }

    // 전화번호 변경 처리
    String newPhoneNumber = updatedUser.getPhnNumb();
    if (isNotEmpty(newPhoneNumber) && !newPhoneNumber.equals(currentUser.getPhnNumb())) {
      logger.info("Updating phone number for user ID: {}", userId);
      updatePhoneNumber(userId, newPhoneNumber);
      saveChangeHistory("PhoneNumber", currentUser.getPhnNumb(), newPhoneNumber, currentUser);
      isProfileUpdated = true;
    }

    // 주소 업데이트 처리
    String newAddress = addressDto.getAddress();
    if (isNotEmpty(newAddress) && !newAddress.equals(currentAddress.getAddress())) {
      logger.info("Updating address for user ID: {}", userId);
      addressDto.setUserId(userId);  // userId 설정
      shippingAddressService.updateAddress(addressDto);
      saveChangeHistory("Address", currentAddress.getAddress(), newAddress, currentUser);
      isAddressUpdated = true;
    }

    return isProfileUpdated || isAddressUpdated;
  }

  // 변경 기록 저장 메서드
  private void saveChangeHistory(String fieldName, String beforeValue, String afterValue, MemberDto currentUser) {
    InfoChangeHistoryDto changeLog = new InfoChangeHistoryDto();
    changeLog.setId(currentUser.getId());
    changeLog.setChgTime(LocalDate.now());
    changeLog.setChgInfo(fieldName);
    changeLog.setBforChg(beforeValue);
    changeLog.setAftrChg(afterValue);
    changeLog.setRegDate(LocalDate.now());
    changeLog.setRegId(currentUser.getId());
    changeLog.setUpDate(LocalDate.now());
    changeLog.setUpId(currentUser.getId());

    // 변경 기록 저장
    infoChangeHistoryDao.saveChangeHistory(changeLog);

  }

  @Override
  public void updatePassword(String id, String newPassword) {
    logger.info("Updating password for ID: {}", id);
    MemberDto member = memberDao.selectMemberById(id);
    if (member == null) {
      throw new RuntimeException("회원이 존재하지 않습니다.");
    }
    member.setPswd(newPassword);  // 암호화는 추후 처리
    memberDao.updateMember(member);
    logger.info("Password updated successfully for ID: {}", id);
  }

  @Override
  @Transactional
  public void updateEmail(String userId, String newEmail) {
    logger.info("Updating email for ID: {}", userId);
    MemberDto member = memberDao.selectMemberById(userId);
    if (member == null) {
      throw new RuntimeException("회원이 존재하지 않습니다.");
    }
    member.setEmail(newEmail);
    memberDao.updateMember(member);
    logger.info("Email updated successfully for ID: {}", userId);
  }

  @Override
  @Transactional
  public void updatePhoneNumber(String userId, String newPhoneNumber) {
    logger.info("Updating phone number for ID: {}", userId);
    MemberDto member = memberDao.selectMemberById(userId);
    if (member == null) {
      throw new RuntimeException("회원이 존재하지 않습니다.");
    }
    member.setPhnNumb(newPhoneNumber);
    memberDao.updateMember(member);
    logger.info("Phone number updated successfully for ID: {}", userId);
  }

  private boolean isNotEmpty(String value) {
    return value != null && !value.trim().isEmpty();
  }

  @Override
  public boolean isTokenValid(String token) {
    logger.info("Validating token: {}", token);
    MemberDto member = memberDao.selectMemberByToken(token);
    boolean isValid = member != null && member.getMailTokn() != null;
    logger.info("Token validation result: {}", isValid ? "valid" : "invalid");
    return isValid;
  }

}
