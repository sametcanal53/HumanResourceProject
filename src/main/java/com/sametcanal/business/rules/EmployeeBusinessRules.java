package com.sametcanal.business.rules;

import com.sametcanal.core.utilities.exception.HumanResourceExceptionConstant;
import com.sametcanal.dataAccess.abstracts.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeBusinessRules {
    private final EmployeeRepository employeeRepository;


    public void checkIfEmployeeExists(Long id) {
        if (!this.employeeRepository.existsById(id)) {
            log.error("Employee Not Found! ");
            throw HumanResourceExceptionConstant.EMPLOYEE_NOT_FOUND;
        }
    }

    public void checkIfSalary(double salary){
        if(salary<1000){
            throw HumanResourceExceptionConstant.SALARY_VALIDATION_ERROR;
        }
    }

}
