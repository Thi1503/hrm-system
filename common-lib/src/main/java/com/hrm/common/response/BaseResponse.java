package com.hrm.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {

    private boolean success;
    private String code;
    private String message;
    private T data;

    /* ===== SUCCESS ===== */

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(true, "SUCCESS", "OK", data);
    }

    public static <T> BaseResponse<T> success(String message, T data) {
        return new BaseResponse<>(true, "SUCCESS", message, data);
    }

    /* ===== ERROR ===== */

    public static <T> BaseResponse<T> error(String code, String message) {
        return new BaseResponse<>(false, code, message, null);
    }
}
