//주소 추가 기능 테스트 안함

//package com.fastcampus.ch4.service.member;
//
//import com.fastcampus.ch4.dao.member.TermAgreeDao;
//import com.fastcampus.ch4.dao.member.TermDao;
//import com.fastcampus.ch4.dto.member.MemberDto;
//import com.fastcampus.ch4.dto.member.TermDto;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//public class SignUpServiceImplTest {
//
//  @InjectMocks
//  private SignUpServiceImpl signUpService;
//
//  @Mock
//  private MemberManagementService memberManagementService;
//
//  @Mock
//  private TermDao termDao;
//
//  @Mock
//  private TermAgreeDao termAgreeDao;
//
//  @Mock
//  private TermService termService;
//
//  private MemberDto mockMember;
//  private List<TermDto> mockTerms;
//
//  @Before
//  public void setUp() {
//    MockitoAnnotations.openMocks(this);
//
//    // Mock member
//    mockMember = new MemberDto();
//    mockMember.setId("testMember");
//
//    // Mock terms (필수 약관 1개, 선택 약관 2개)
//    mockTerms = List.of(
//            new TermDto(1, "필수 약관 1", "이것은 필수 약관입니다.", "Y"),
//            new TermDto(2, "선택 약관 1", "이것은 선택 약관입니다.", "N"),
//            new TermDto(3, "선택 약관 2", "이것은 선택 약관입니다.", "N")
//    );
//
//    when(termService.getAllTerms()).thenReturn(mockTerms);
//  }
//
//  @Test
//  public void testProcessSignup_WithOptionalTerms() {
//    // Given: 필수 약관 동의 및 선택 약관 동의/비동의
//    List<Integer> requiredTerms = List.of(1); // 필수 약관 동의
//    List<Integer> optionalTerms = List.of(2); // 선택 약관 1 동의, 선택 약관 2는 동의 안함
//
//    // When: 회원 가입 프로세스 실행
//    signUpService.processSignup(mockMember, requiredTerms, optionalTerms);
//
//    // Then: 회원 정보가 저장되었는지 확인
//    verify(memberManagementService, times(1)).addMember(any(MemberDto.class));
//
//    // Then: 선택 약관 동의 정보가 저장되었는지 확인
//    verify(termAgreeDao, times(1)).insertTermAgreements(argThat(termAgreeDtos -> {
//      // 선택 약관의 동의 여부를 확인
//      long agreedCount = termAgreeDtos.stream()
//              .filter(dto -> dto.getTermId() == 2 && dto.getTermAgree().equals("Y"))
//              .count();
//      long notAgreedCount = termAgreeDtos.stream()
//              .filter(dto -> dto.getTermId() == 3 && dto.getTermAgree().equals("N"))
//              .count();
//
//      // 선택 약관 1은 동의(Y), 선택 약관 2는 비동의(N)
//      return agreedCount == 1 && notAgreedCount == 1;
//    }));
//  }
//
//  @Test
//  public void testProcessSignup_WithoutOptionalTerms() {
//    // Given: 필수 약관만 동의, 선택 약관은 모두 비동의
//    List<Integer> requiredTerms = List.of(1); // 필수 약관 동의
//    List<Integer> optionalTerms = List.of();  // 선택 약관에 동의하지 않음
//
//    // When: 회원 가입 프로세스 실행
//    signUpService.processSignup(mockMember, requiredTerms, optionalTerms);
//
//    // Then: 회원 정보가 저장되었는지 확인
//    verify(memberManagementService, times(1)).addMember(any(MemberDto.class));
//
//    // Then: 선택 약관 동의 정보가 저장되었는지 확인
//    verify(termAgreeDao, times(1)).insertTermAgreements(argThat(termAgreeDtos -> {
//      // 모든 선택 약관이 비동의(N) 상태인지 확인
//      long notAgreedCount = termAgreeDtos.stream()
//              .filter(dto -> dto.getTermAgree().equals("N"))
//              .count();
//
//      // 선택 약관 2개에 대해 모두 비동의(N)
//      return notAgreedCount == 3;
//    }));
//  }
//}
