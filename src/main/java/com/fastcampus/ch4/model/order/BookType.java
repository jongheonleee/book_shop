package com.fastcampus.ch4.model.order;

public enum BookType {
    PRINTED("printed"),
    EBOOK("ebook");

    private final String code;

    BookType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static boolean isValid(Object obj) {
        if (obj == null) return false;

        for (BookType bookType : BookType.values()) {
            if (bookType.getCode().equals(obj.toString())) {
                return true;
            }
        }

        return false;
    }

    public boolean isSameType(String typeCode) {
        return typeCode.equals(this.code);
    }
}
