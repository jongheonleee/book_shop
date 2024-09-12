package com.fastcampus.ch4.validator.item;

import com.fastcampus.ch4.dto.item.BookDto;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class BookDtoValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        // clazz가 BookDto 또는 그 자손인지 확인
        return BookDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BookDto bookDto = (BookDto) target;
        String isbn = bookDto.getIsbn();

//        if (isbn == null || "".equals(isbn.trim())) {
//            errors.rejectValue("isbn", "required");
//        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "isbn", "required");
        if (isbn.length() != 13) {
            errors.rejectValue("isbn", "invalidLength");
        }
    }
}
