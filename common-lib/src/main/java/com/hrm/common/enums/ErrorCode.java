package com.hrm.common.enums;

public enum ErrorCode {

    // COMMON
    SUCCESS,
    INVALID_REQUEST,
    NOT_FOUND,
    UNAUTHORIZED,
    FORBIDDEN,
    SYSTEM_ERROR,

    // AUTH
    USERNAME_OR_PASSWORD_INVALID,
    ACCOUNT_LOCKED,

    // BUSINESS
    DATA_ALREADY_EXISTS
}
