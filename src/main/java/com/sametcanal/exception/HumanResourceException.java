package com.sametcanal.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HumanResourceException extends RuntimeException {
    private String errorCode;
    private String errorMessage;
    private HttpStatus httpStatus;
}
