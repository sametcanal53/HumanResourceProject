package com.sametcanal.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HumanResourceExceptionObject {
    private String errorCode;
    private Object errorMessage;
    private HttpStatus httpStatus;
}
