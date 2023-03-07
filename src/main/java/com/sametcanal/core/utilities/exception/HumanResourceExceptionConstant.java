package com.sametcanal.core.utilities.exception;

import org.springframework.http.HttpStatus;

public class HumanResourceExceptionConstant{

    public static HumanResourceException EMPLOYEE_NOT_FOUND =
            new HumanResourceException("HRP-2001", "Employee Not Found", HttpStatus.NOT_FOUND);

    public static HumanResourceException HUMAN_RESOURCE_NOT_FOUND =
            new HumanResourceException("HRP-3001", "Human Resource Not Found", HttpStatus.NOT_FOUND);

    public static HumanResourceException EMAIL_ALREADY_EXISTS =
            new HumanResourceException("HRP-4001","Email already exists", HttpStatus.BAD_REQUEST);

    public static HumanResourceException USERNAME_ALREADY_EXISTS =
                 new HumanResourceException("HRP-4002","Username already exists", HttpStatus.BAD_REQUEST);

    public static HumanResourceException ROLE_NOT_FOUND =
            new HumanResourceException("HRP-5001","Role Not Found", HttpStatus.BAD_REQUEST);

}
