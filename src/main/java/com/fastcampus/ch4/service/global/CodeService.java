package com.fastcampus.ch4.service.global;

import com.fastcampus.ch4.dto.global.CodeDto;

public interface CodeService {
    /*
        메서드 이름 : findByCodeName
        기능
        - 코드명으로 코드를 조회한다.
        - Cache를 사용한다.
        매개변수
        - codeName : String : 코드명
        반환값
        - CodeDto : 코드 정보
         */
    CodeDto findByCodeName(String codeName);

    /*
        method name : findByCode
        parameter
        - code : String : code (ex. "ord-stat-01")
        return : CodeDto : code information
        description
        - Find code by code
        - Use Cache
         */
    CodeDto findByCode(String code);
}
