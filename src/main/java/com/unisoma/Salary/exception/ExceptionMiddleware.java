package com.unisoma.Salary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionMiddleware extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(ApiException ex) {

        ErrorResponse response = new ErrorResponse();
        response.setStatus(ex.getStatus().value());
        response.setMessage(ex.getMessage());
        response.setTimestamp(System.currentTimeMillis());
        response.setDebugMessage(ex.getDebugMessage());
        response.setSubErrors(ex.getSubErrors());

        return ResponseEntity.status(ex.getStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {

        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage("An internal error occurred on the server.");
        response.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
