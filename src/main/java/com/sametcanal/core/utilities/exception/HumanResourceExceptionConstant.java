package com.sametcanal.core.utilities.exception;

import org.springframework.http.HttpStatus;

public class HumanResourceExceptionConstant{

    // ExceptionHandler / HRP-1XXX

    public static HumanResourceException NESTED_OBJECT_EXCEPTION =
            new HumanResourceException("HRP-1001", "Nested object exception", HttpStatus.BAD_REQUEST);

    // Employee / HRP-2XXX
    public static HumanResourceException EMPLOYEE_NOT_FOUND =
            new HumanResourceException("HRP-2001", "Employee Not Found", HttpStatus.NOT_FOUND);

    // Human Resource / HRP-3XXX
    public static HumanResourceException HUMAN_RESOURCE_NOT_FOUND =
            new HumanResourceException("HRP-3001", "Human Resource Not Found", HttpStatus.NOT_FOUND);

    // User-Auth / HRP-4XXX
    public static HumanResourceException EMAIL_ALREADY_EXISTS =
            new HumanResourceException("HRP-4001","Email already exists", HttpStatus.BAD_REQUEST);

    public static HumanResourceException USERNAME_ALREADY_EXISTS =
                 new HumanResourceException("HRP-4002","Username already exists", HttpStatus.BAD_REQUEST);

    // Role / HRP-5XXX
    public static HumanResourceException ROLE_NOT_FOUND =
            new HumanResourceException("HRP-5001","Role Not Found", HttpStatus.BAD_REQUEST);

}
