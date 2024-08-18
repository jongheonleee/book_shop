package com.fastcampus.ch4.service.member;

import com.fastcampus.ch4.dao.member.TermAgreeDao;
import com.fastcampus.ch4.dao.member.TermDao;
import com.fastcampus.ch4.dto.member.TermAgreeDto;
import com.fastcampus.ch4.dto.member.TermDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TermServiceImplTest {

  @Mock
  private TermDao termDao;

  @Mock
  private TermAgreeDao termAgreeDao;

  @InjectMocks
  private TermServiceImpl termService;

  private TermDto testTerm1;
  private TermDto testTerm2;
  private TermAgreeDto termAgreeDto1;
  private TermAgreeDto termAgreeDto2;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    testTerm1 = new TermDto(1, "Test Term 1", "Test Content 1", "Y", LocalDateTime.now(), "testuser1", LocalDateTime.now(), "testuser1");
    testTerm2 = new TermDto(2, "Test Term 2", "Test Content 2", "N", LocalDateTime.now(), "testuser2", LocalDateTime.now(), "testuser2");

    termAgreeDto1 = new TermAgreeDto("testUser", 1, "Y", LocalDateTime.now(), "testUser", LocalDateTime.now(), "testUser");
    termAgreeDto2 = new TermAgreeDto("testUser", 2, "N", LocalDateTime.now(), "testUser", LocalDateTime.now(), "testUser");
  }

  @Test
  public void testGetAllTerms() {
    when(termDao.getAllTerms()).thenReturn(Arrays.asList(testTerm1, testTerm2));

    List<TermDto> terms = termService.getAllTerms();
    assertNotNull(terms);
    assertEquals(2, terms.size());
    assertTrue(terms.stream().anyMatch(t -> t.getTermId() == testTerm1.getTermId()));
    assertTrue(terms.stream().anyMatch(t -> t.getTermId() == testTerm2.getTermId()));
  }

  @Test
  public void testGetTermById() {
    when(termDao.getTermById(testTerm1.getTermId())).thenReturn(testTerm1);

    TermDto term = termService.getTermById(testTerm1.getTermId());
    assertNotNull(term);
    assertEquals(testTerm1.getTermId(), term.getTermId());
    assertEquals(testTerm1.getTermName(), term.getTermName());
    assertEquals(testTerm1.getTermContent(), term.getTermContent());
  }

  @Test
  public void testValidateTermAgreements_Valid() {
    when(termDao.getAllTerms()).thenReturn(Arrays.asList(testTerm1, testTerm2));

    List<TermAgreeDto> termAgreeDtos = Arrays.asList(termAgreeDto1);
    termService.validateTermAgreements(termAgreeDtos);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateTermAgreements_Invalid() {
    when(termDao.getAllTerms()).thenReturn(Arrays.asList(testTerm1, testTerm2));

    List<TermAgreeDto> termAgreeDtos = Collections.singletonList(termAgreeDto2);
    termService.validateTermAgreements(termAgreeDtos);
  }

    @Test
    public void testSaveTermAgreements() {
      // Given
      TermAgreeDto termAgreeDto1 = new TermAgreeDto();
      termAgreeDto1.setId("user1");
      termAgreeDto1.setTermId(1);
      termAgreeDto1.setTermAgree("Y");
      termAgreeDto1.setRegDate(LocalDateTime.now());
      termAgreeDto1.setRegId("user1");
      termAgreeDto1.setUpDate(LocalDateTime.now());
      termAgreeDto1.setUpId("user1");

      TermAgreeDto termAgreeDto2 = new TermAgreeDto();
      termAgreeDto2.setId("user2");
      termAgreeDto2.setTermId(2);
      termAgreeDto2.setTermAgree("N");
      termAgreeDto2.setRegDate(LocalDateTime.now());
      termAgreeDto2.setRegId("user2");
      termAgreeDto2.setUpDate(LocalDateTime.now());
      termAgreeDto2.setUpId("user2");

      List<TermAgreeDto> termAgreeDtos = Arrays.asList(termAgreeDto1, termAgreeDto2);

      // When
      termService.saveTermAgreements(termAgreeDtos);

      // Then
      verify(termAgreeDao, times(1)).insertTermAgreements(termAgreeDtos);
    }


  @Test
  public void testGetAllTermIds() {
    when(termDao.getAllTermIds()).thenReturn(Arrays.asList(1, 2));

    List<Integer> termIds = termService.getAllTermIds();
    assertNotNull(termIds);
    assertEquals(2, termIds.size());
    assertTrue(termIds.contains(1));
    assertTrue(termIds.contains(2));
  }

  @Test
  public void testGetTermAgreements() {
    when(termAgreeDao.getTermAgreements("testUser")).thenReturn(Arrays.asList(termAgreeDto1, termAgreeDto2));

    List<TermAgreeDto> termAgreeDtos = termService.getTermAgreements("testUser");
    assertNotNull(termAgreeDtos);
    assertEquals(2, termAgreeDtos.size());
    assertTrue(termAgreeDtos.stream().anyMatch(dto -> dto.getTermId() == 1));
    assertTrue(termAgreeDtos.stream().anyMatch(dto -> dto.getTermId() == 2));
  }
}
