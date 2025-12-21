package com.hrm.employeeservice.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.hrm.common.enums.ErrorCode;
import com.hrm.common.exception.BusinessException;
import com.hrm.common.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * BUSINESS EXCEPTION
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BaseResponse<Void>> handleBusinessException(
            BusinessException exception) {

        BaseResponse<Void> response = BaseResponse.error(
                exception.getErrorCode().name(),
                exception.getMessage()
        );

        return ResponseEntity.ok(response);
    }

    /**
     * VALIDATION EXCEPTION (@Valid)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Void>> handleValidationException(
            MethodArgumentNotValidException exception) {

        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .orElse("Invalid request");

        BaseResponse<Void> response = BaseResponse.error(
                ErrorCode.INVALID_REQUEST.name(),
                message
        );

        return ResponseEntity.ok(response);
    }

    /**
     * ILLEGAL ARGUMENT
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponse<Void>> handleIllegalArgument(
            IllegalArgumentException exception) {

        BaseResponse<Void> response = BaseResponse.error(
                ErrorCode.INVALID_REQUEST.name(),
                exception.getMessage()
        );

        return ResponseEntity.ok(response);
    }

    /**
     * ALL OTHER EXCEPTIONS (FALLBACK)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Void>> handleException(Exception exception) {

        log.error("Unhandled exception", exception);

        BaseResponse<Void> response = BaseResponse.error(
                ErrorCode.SYSTEM_ERROR.name(),
                "System error"
        );

        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<Void>> handleHttpMessageNotReadable(
            HttpMessageNotReadableException exception) {

        String message = "Invalid request body";

        Throwable rootCause = exception.getMostSpecificCause();
        if (rootCause instanceof InvalidFormatException invalidEx) {

            String fieldName = invalidEx.getPath()
                    .stream()
                    .reduce((first, second) -> second)
                    .map(JsonMappingException.Reference::getFieldName)
                    .orElse("unknown");

            String invalidValue = String.valueOf(invalidEx.getValue());
            String targetType = invalidEx.getTargetType().getSimpleName();

            message = String.format(
                    "Field '%s' has invalid value '%s', expected type %s",
                    fieldName,
                    invalidValue,
                    targetType
            );
        }

        return ResponseEntity.ok(
                BaseResponse.error(
                        ErrorCode.INVALID_REQUEST.name(),
                        message
                )
        );
    }


}
