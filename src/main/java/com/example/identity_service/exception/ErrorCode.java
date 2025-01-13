package com.example.identity_service.exception;

public enum ErrorCode {
    UNCATEGORIZED(9999, "Uncategorized error"),
    USER_EXISTS(1001, "User already exists"),
    INVALID_KEY(9998, "Invalid message key"),
    USER_NOT_FOUND(1002, "User not found"),
    USERNAME_INVALID(1003, "Username must be at least 5 characters long"),
    PASSWORD_INVALID(1004, "Password must be at least 8 characters long")
    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
