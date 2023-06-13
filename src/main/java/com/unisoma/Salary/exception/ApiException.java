package com.unisoma.Salary.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private HttpStatus status;
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private List<ApiSubError> subErrors;

    private ApiException() {
        timestamp = LocalDateTime.now();
    }

    ApiException(HttpStatus status) {
        this();
        this.status = status;
    }

    public ApiException(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ApiException(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ApiException(HttpStatus status, String message) {
        this();
        this.status = status;
        this.message = message;
    }

    public ApiException(String message) {
        this();
        this.status = HttpStatus.BAD_REQUEST;
        this.message = message;
    }

}
