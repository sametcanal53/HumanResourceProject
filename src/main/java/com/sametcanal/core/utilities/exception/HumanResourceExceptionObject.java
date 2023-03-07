package com.sametcanal.core.utilities.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HumanResourceExceptionObject extends RuntimeException{
    private String errorCode;
    private Object errorMessage;
    private HttpStatus httpStatus;
}
