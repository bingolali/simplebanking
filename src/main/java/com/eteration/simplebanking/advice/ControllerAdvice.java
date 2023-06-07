package com.eteration.simplebanking.advice;

import com.eteration.simplebanking.exception.BusinessNotFoundException;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;


/**
 * @author bingolalii
 */
@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = BusinessNotFoundException.class)
    public ResponseEntity<ErrorResponse> businessNotFoundException(BusinessNotFoundException e) {

        log.error(String.format("Business not found: %s", e.getMessage()), e);

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(e.getErrorCode());
        errorResponse.setDescription(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(102);
        errorResponse.setDescription("Request validation error");
        errorResponse.setValidationErrors(new ArrayList<>());

        e.getFieldErrors().forEach(fieldError -> {
            errorResponse.getValidationErrors().add(fieldError.getDefaultMessage());
            log.error(String.format("Request validation error: %s", fieldError.getDefaultMessage()), e);
        });

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse> insufficientBalanceException(InsufficientBalanceException e) {

        log.error(String.format("Insufficient balance: %s", e.getMessage()), e);

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(e.getErrorCode());
        errorResponse.setDescription(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
