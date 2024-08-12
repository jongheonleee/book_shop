package com.fastcampus.ch4.service.order.fake;

import com.fastcampus.ch4.dto.order.temp.TempBookDto;

import java.util.List;

public class FakeBookServiceImpl implements TempBookService {
    List<TempBookDto> BookDtoList = List.of(new TempBookDto[]{
            new TempBookDto("9788994492032", 3, "자바의 정석", 30000, null, 0.1, null, 0.1, null, 3000, null),
            new TempBookDto("9788966261024", 1, "테스트 주도 개발", 22000, null, 0.1, null, 0.1, null, 2200, null),
            new TempBookDto("9791169212427", 1, "켄트 벡의 Tidy First?: 더 나은 소프트웨어 설계를 위한 32가지 코드 정리법", 16800, 15840, 0.1, 0.1, 0.1, 0.1, 1680, 1584),
            new TempBookDto("9791158395155", 1, "자바/스프링 개발자를 위한 실용주의 프로그래밍", 32000, 23040, 0.1, 0.1, 0.1, 0.1, 3200, 2304),
            new TempBookDto("9791162245408", 1, "유연한 소프트웨어를 만드는 설계 원칙", 31500, 25200, 0.1, 0.1, 0.1, 0.1, 3150, 2520),
            new TempBookDto("9788966263615", 1, "한 줄 한 줄 짜면서 익히는 러스트 프로그래밍", 31500, 25200, 0.1, 0.1, 0.1, 0.1, 3150, 2520),
            new TempBookDto("9791158394882", 1, "실전! 러스트로 배우는 리눅스 커널 프로그래밍", 31500, 25200, 0.1, 0.1, 0.1, 0.1, 3150, 2520),
            new TempBookDto("9788966264414", 1, "JVM 밑바닥까지 파헤치기", 38700, 30960, 0.1, 0.1, 0.1, 0.1, 3870, 3096),
    });

    @Override
    public TempBookDto getBookByIsbn(String isbn) {
        for (TempBookDto bookDto : BookDtoList) {
            if (bookDto.getIsbn().equals(isbn)) {
                return bookDto;
            }
        }
        return null;
    }
}