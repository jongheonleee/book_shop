package com.fastcampus.ch4.model.cart;

public enum UserType {
    MEMBERSHIP("membership"),
    NON_MEMBERSHIP("non_membership");

    private final String code;

    UserType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static boolean isValid(Object obj) {
        if (obj == null) return false;

        for (UserType userType : UserType.values()) {
            if (userType.getCode().equals(obj.toString())) {
                return true;
            }
        }

        return false;
    }
}
