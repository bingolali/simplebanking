package com.eteration.simplebanking.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsufficientBalanceException extends RuntimeException {

    private int errorCode;

    public InsufficientBalanceException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
