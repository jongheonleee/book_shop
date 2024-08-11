package com.fastcampus.ch4.model.temp;

public enum TempBookType {
    PRINTED_BOOK("printed", "도서"),
    E_BOOK("ebook", "EBOOK");

    private final String code;
    private final String codeName;

    TempBookType(String bookType, String typeName) {
        this.code = bookType;
        this.codeName = typeName;
    }

    public boolean isSameBookType (String bookType) {
        return bookType.equals(bookType);
    }

    public String getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }
}
