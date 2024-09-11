package com.fastcampus.ch4.domain.item;

public enum BookType {
    PAPER("paper"),
    E_BOOK("ebook");

    private final String code;

    BookType(String code) {
        this.code = code;
    }

    public static BookType of(String prodTypeCode) {
        if ("paper".equals(prodTypeCode)) {
            return PAPER;
        } else if ("ebook".equals(prodTypeCode)) {
            return E_BOOK;
        }
        return null;
    }

    public String getCode() {
        return code;
    }
}
