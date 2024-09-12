package com.fastcampus.ch4.service.global;

import com.fastcampus.ch4.dao.global.CodeDao;
import com.fastcampus.ch4.dto.global.CodeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CodeServiceImpl implements CodeService {
    @Autowired
    private CodeDao codeDao;

    // TODO : thread safe 한 자료구조를 사용하도록 수정
    // cache 저장소
    private final List<CodeDto> codeList = new ArrayList<>();

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
    @Override
    public CodeDto findByCodeName(String codeName) {
        CodeDto memoryCodeDto = codeList.stream()
                .filter(codeDto -> codeDto.getCode_name().equals(codeName))
                .findFirst()
                .orElse(null);

        if (memoryCodeDto != null)
            return memoryCodeDto;

        // DB 조회
        CodeDto codeDto = codeDao.selectByCodeName(codeName);

        // 메모리에 추가
        addCodeInMemory(codeDto);

        return codeDto;
    }

    /*
    method name : findByCode
    parameter
    - code : String : code (ex. "ord-stat-01")
    return : CodeDto : code information
    description
    - Find code by code
    - Use Cache
     */
    @Override
    public CodeDto findByCode(String code) {
        CodeDto memoryCodeDto = codeList.stream()
                .filter(codeDto -> {
                    return Objects.equals(codeDto.getCode(), code);
                })
                .findFirst()
                .orElse(null);

        if (memoryCodeDto != null)
            return memoryCodeDto;

        // DB 조회
        CodeDto codeDto = codeDao.selectByCode(code);

        // 메모리에 추가
        addCodeInMemory(codeDto);

        return codeDto;
    }

    /*
    메서드 이름 : addCodeInMemory
    매개변수
    - codeDto : CodeDto : 코드 정보
    반환값 : 없음
    기능
    - 코드 정보를 메모리에 추가한다.
     */
    private void addCodeInMemory(CodeDto codeDto) {
        codeList.add(codeDto);
    }
}
