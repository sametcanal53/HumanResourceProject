package com.sametcanal.core.utilities.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HumanResourceException extends RuntimeException {

    private String errorCode;
    private Object errorMessage;
    private HttpStatus httpStatus;

    HumanResourceException(String errorCode,String errorMessage,HttpStatus httpStatus){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

}
