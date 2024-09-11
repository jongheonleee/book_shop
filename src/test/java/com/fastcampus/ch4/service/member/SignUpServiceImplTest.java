//package com.fastcampus.ch4.service.member;
//
//import com.fastcampus.ch4.dto.member.MemberDto;
//import com.fastcampus.ch4.dto.member.TermAgreeDto;
//import com.fastcampus.ch4.dto.member.TermDto;
//import com.fastcampus.ch4.service.member.SignUpServiceImpl;
//import com.fastcampus.ch4.service.member.MemberManagementService;
//import com.fastcampus.ch4.dao.member.TermAgreeDao;
//import com.fastcampus.ch4.service.member.TermService;
//import com.fastcampus.ch4.service.member.ShippingAddressService;
//import com.fastcampus.ch4.dto.member.ShippingAddressDto;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
//public class SignUpServiceImplTest {
//
//    @Mock
//    private MemberManagementService memberManagementService;
//
//    @Mock
//    private TermAgreeDao termAgreeDao;
//
//    @Mock
//    private TermService termService;
//
//    @Mock
//    private ShippingAddressService shippingAddressService;
//
//    @InjectMocks
//    private SignUpServiceImpl signUpService;
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void testProcessSignup_WithOptionalTerm3Agreed() {
//        // Given
//        MemberDto member = new MemberDto();
//        member.setId("testUser");
//
//        List<Integer> requiredTerms = List.of(1, 2);
//        List<Integer> optionalTerms = List.of(3); // User agreed to term with ID 3
//        String address = "123 Test St";
//
//        when(termService.getAllTerms()).thenReturn(List.of(
//                new TermDto(1, "Term 1", "Content 1", "Y", null, null, null, null),
//                new TermDto(2, "Term 2", "Content 2", "Y", null, null, null, null),
//                new TermDto(3, "Term 3", "Content 3", "N", null, null, null, null),
//                new TermDto(4, "Term 4", "Content 4", "N", null, null, null, null)
//        ));
//
//        // When
//        signUpService.processSignup(member, requiredTerms, optionalTerms, address);
//
//        // Capture the arguments passed to insertTermAgreements
//        ArgumentCaptor<List<TermAgreeDto>> captor = ArgumentCaptor.forClass(List.class);
//        verify(termAgreeDao).insertTermAgreements(captor.capture());
//
//        List<TermAgreeDto> capturedDtos = captor.getValue();
//
//        // Then
//        assertEquals(4, capturedDtos.size()); // 총 4개의 약관 (필수 2개, 선택 2개)
//        assertEquals("Y", findTermAgreeById(capturedDtos, 3).getTermAgree());
//        assertEquals("N", findTermAgreeById(capturedDtos, 4).getTermAgree());
//        assertEquals("N", findTermAgreeById(capturedDtos, 1).getTermAgree()); // 선택 약관에 대해서만 비동의 처리
//        assertEquals("N", findTermAgreeById(capturedDtos, 2).getTermAgree()); // 선택 약관에 대해서만 비동의 처리
//    }
//
//    @Test
//    public void testProcessSignup_WithOptionalTerm4Agreed() {
//        // Given
//        MemberDto member = new MemberDto();
//        member.setId("testUser");
//
//        List<Integer> requiredTerms = List.of(1, 2);
//        List<Integer> optionalTerms = List.of(4); // User agreed to term with ID 4
//        String address = "123 Test St";
//
//        when(termService.getAllTerms()).thenReturn(List.of(
//                new TermDto(1, "Term 1", "Content 1", "Y", null, null, null, null),
//                new TermDto(2, "Term 2", "Content 2", "Y", null, null, null, null),
//                new TermDto(3, "Term 3", "Content 3", "N", null, null, null, null),
//                new TermDto(4, "Term 4", "Content 4", "N", null, null, null, null)
//        ));
//
//        // When
//        signUpService.processSignup(member, requiredTerms, optionalTerms, address);
//
//        // Capture the arguments passed to insertTermAgreements
//        ArgumentCaptor<List<TermAgreeDto>> captor = ArgumentCaptor.forClass(List.class);
//        verify(termAgreeDao).insertTermAgreements(captor.capture());
//
//        List<TermAgreeDto> capturedDtos = captor.getValue();
//
//        // Then
//        assertEquals(4, capturedDtos.size());
//        assertEquals("N", findTermAgreeById(capturedDtos, 3).getTermAgree());
//        assertEquals("Y", findTermAgreeById(capturedDtos, 4).getTermAgree());
//        assertEquals("N", findTermAgreeById(capturedDtos, 1).getTermAgree());
//        assertEquals("N", findTermAgreeById(capturedDtos, 2).getTermAgree());
//    }
//
//    @Test
//    public void testProcessSignup_WithNoOptionalTermsAgreed() {
//        // Given
//        MemberDto member = new MemberDto();
//        member.setId("testUser");
//
//        List<Integer> requiredTerms = List.of(1, 2);
//        List<Integer> optionalTerms = List.of(); // No optional terms agreed
//        String address = "123 Test St";
//
//        when(termService.getAllTerms()).thenReturn(List.of(
//                new TermDto(1, "Term 1", "Content 1", "Y", null, null, null, null),
//                new TermDto(2, "Term 2", "Content 2", "Y", null, null, null, null),
//                new TermDto(3, "Term 3", "Content 3", "N", null, null, null, null),
//                new TermDto(4, "Term 4", "Content 4", "N", null, null, null, null)
//        ));
//
//        // When
//        signUpService.processSignup(member, requiredTerms, optionalTerms, address);
//
//        // Capture the arguments passed to insertTermAgreements
//        ArgumentCaptor<List<TermAgreeDto>> captor = ArgumentCaptor.forClass(List.class);
//        verify(termAgreeDao).insertTermAgreements(captor.capture());
//
//        List<TermAgreeDto> capturedDtos = captor.getValue();
//
//        // Then
//        assertEquals(4, capturedDtos.size());
//        assertEquals("N", findTermAgreeById(capturedDtos, 3).getTermAgree());
//        assertEquals("N", findTermAgreeById(capturedDtos, 4).getTermAgree());
//        assertEquals("N", findTermAgreeById(capturedDtos, 1).getTermAgree());
//        assertEquals("N", findTermAgreeById(capturedDtos, 2).getTermAgree());
//    }
//
//    @Test
//    public void testProcessSignup_WithAllOptionalTermsAgreed() {
//        // Given
//        MemberDto member = new MemberDto();
//        member.setId("testUser");
//
//        List<Integer> requiredTerms = List.of(1, 2);
//        List<Integer> optionalTerms = List.of(3, 4); // All optional terms agreed
//        String address = "123 Test St";
//
//        when(termService.getAllTerms()).thenReturn(List.of(
//                new TermDto(1, "Term 1", "Content 1", "Y", null, null, null, null),
//                new TermDto(2, "Term 2", "Content 2", "Y", null, null, null, null),
//                new TermDto(3, "Term 3", "Content 3", "N", null, null, null, null),
//                new TermDto(4, "Term 4", "Content 4", "N", null, null, null, null)
//        ));
//
//        // When
//        signUpService.processSignup(member, requiredTerms, optionalTerms, address);
//
//        // Capture the arguments passed to insertTermAgreements
//        ArgumentCaptor<List<TermAgreeDto>> captor = ArgumentCaptor.forClass(List.class);
//        verify(termAgreeDao).insertTermAgreements(captor.capture());
//
//        List<TermAgreeDto> capturedDtos = captor.getValue();
//
//        // Then
//        assertEquals(4, capturedDtos.size());
//        assertEquals("Y", findTermAgreeById(capturedDtos, 3).getTermAgree());
//        assertEquals("Y", findTermAgreeById(capturedDtos, 4).getTermAgree());
//        assertEquals("N", findTermAgreeById(capturedDtos, 1).getTermAgree());
//        assertEquals("N", findTermAgreeById(capturedDtos, 2).getTermAgree());
//    }
//
//    private TermAgreeDto findTermAgreeById(List<TermAgreeDto> dtos, int termId) {
//        return dtos.stream()
//                .filter(dto -> dto.getTermId() == termId)
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException("Term ID not found: " + termId));
//    }
//}
