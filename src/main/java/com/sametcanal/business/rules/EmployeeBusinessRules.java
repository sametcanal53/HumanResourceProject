package com.sametcanal.business.rules;

import com.sametcanal.core.utilities.exception.HumanResourceException;
import com.sametcanal.dataAccess.abstracts.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class EmployeeBusinessRules {
    private EmployeeRepository employeeRepository;

    public void checkIfEmployeeExists(Long id){
        if (!this.employeeRepository.existsById(id)) {
            log.error("Employee Not Found! ");
            throw new HumanResourceException("HRP-2001", "Employee Not Found", HttpStatus.NOT_FOUND);
        }
    }
}
