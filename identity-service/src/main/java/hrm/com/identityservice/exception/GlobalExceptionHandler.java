package hrm.com.identityservice.exception;

import com.hrm.common.enums.ErrorCode;
import com.hrm.common.exception.BusinessException;
import com.hrm.common.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
}
