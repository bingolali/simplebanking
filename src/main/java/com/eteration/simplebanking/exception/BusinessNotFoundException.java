package com.eteration.simplebanking.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author bingolalii
 */
@Getter
@Setter
public class BusinessNotFoundException extends RuntimeException{

    private int errorCode;

    public BusinessNotFoundException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
