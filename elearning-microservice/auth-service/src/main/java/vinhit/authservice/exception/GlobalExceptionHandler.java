package vinhit.authservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vinhit.authservice.dto.ApiResponse;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<?>> handleGeneralException (RuntimeException e){
        log.error("Runtime Exception: "+e);
        return ResponseEntity.badRequest().body(ApiResponse.builder()
                .code(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode())
                .message(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage())
                .build());
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<?>> handleAppException (AppException e){
        return ResponseEntity.status(e.getErrorCode().getHttpStatusCode()).body(ApiResponse.builder()
                .code(e.getErrorCode().getCode())
                .message(e.getErrorCode().getMessage())
                .build());
    }
    @ExceptionHandler(value = InternalAuthenticationServiceException.class)
    ResponseEntity<ApiResponse<?>> notValidArgumentUsername (InternalAuthenticationServiceException e){
        return ResponseEntity.ok().body(ApiResponse.builder()
                .code(ErrorCode.USER_NOT_EXISTED.getCode())
                .message(ErrorCode.USER_NOT_EXISTED.getMessage())
                .build());
    }
//    @ExceptionHandler(value = JwtException.class)
//    ResponseEntity<ApiResponse<?>> notValidToken (JwtException e){
//        return ResponseEntity.ok().body(ApiResponse.builder()
//                .code(ErrorCode.INVALID_TOKEN.getCode())
//                .message(ErrorCode.INVALID_TOKEN.getMessage())
//                .build());
//    }
}
