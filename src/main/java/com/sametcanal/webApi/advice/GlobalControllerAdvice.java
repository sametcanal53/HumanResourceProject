package com.sametcanal.webApi.advice;

import com.sametcanal.exception.HumanResourceException;
import com.sametcanal.exception.HumanResourceExceptionObject;
import com.sametcanal.webApi.advice.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    private static final String CONTENT_TYPE_VALUE = "application/json";
    private static final String CONTENT_TYPE = "Content-Type";

    private static final String RETURN_CODE_HEADER = "hrp-return-code";
    private static final String RETURN_MESSAGE_HEADER = "hrp-return-message";

    @ExceptionHandler(HumanResourceException.class)
    public ResponseEntity<Object> handle(HumanResourceException e) {
        log.error(e.getMessage(), e);

        String errorMessage = e.getErrorMessage();
        String errorCode = e.getErrorCode();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(CONTENT_TYPE, CONTENT_TYPE_VALUE);
        responseHeaders.add(RETURN_CODE_HEADER, errorCode);
        responseHeaders.add(RETURN_MESSAGE_HEADER, errorMessage);

        ErrorResponse responseBody = ErrorResponse.builder()
                .errorType(e.getClass().getName())
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .build();

        return ResponseEntity
                .status(e.getHttpStatus())
                .headers(responseHeaders)
                .body(responseBody);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> nestedObjectExceptionHandling(DataIntegrityViolationException exceptions) {
        return new ResponseEntity<HumanResourceExceptionObject>(new HumanResourceExceptionObject("HRS-1001", "Nested object exception", HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException exceptions) {
        Map<String, String> errors = new HashMap<String, String>();
        for (FieldError fieldError : exceptions.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ResponseEntity<Object>(new HumanResourceExceptionObject("HRS-1002", errors, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

}
