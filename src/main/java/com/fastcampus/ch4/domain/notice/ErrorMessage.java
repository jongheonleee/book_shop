package com.fastcampus.ch4.domain.notice;

public enum ErrorMessage {
    ADMIN_LOGIN_REQUIRED("작업 권한이 없습니다. 관리자 계정으로 로그인하세요."),
    INVALID_CODE("존재하지 않거나 비활성화된 카테고리 코드입니다."),
    INVALID_DATA("작업에 실패했습니다. 유효한 값을 입력해 주세요."),
    CONTACT_ADMIN("작업에 실패했습니다. 관리자에게 문의해 주세요."),
    TRY_AGAIN_LATER("작업에 실패했습니다. 잠시 후 다시 시도해 주세요."),
    INVALID_POST("유효하지 않은 페이지입니다.");


    private String msg;

    ErrorMessage(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
