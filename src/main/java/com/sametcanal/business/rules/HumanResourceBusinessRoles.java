package com.sametcanal.business.rules;

import com.sametcanal.business.requests.ChangeDayOff;
import com.sametcanal.business.requests.ChangeSalary;
import com.sametcanal.core.utilities.exception.HumanResourceException;
import com.sametcanal.core.utilities.exception.HumanResourceExceptionConstant;
import com.sametcanal.dataAccess.abstracts.EmployeeRepository;
import com.sametcanal.dataAccess.abstracts.HumanResourceRepository;
import com.sametcanal.entitites.concretes.Employee;
import com.sametcanal.entitites.concretes.HumanResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class HumanResourceBusinessRoles {
    private final HumanResourceRepository humanResourceRepository;

    public void checkIfHumanResourceExists(Long id) {
        if (!this.humanResourceRepository.existsById(id)) {
            log.error("Human Resource Not Found!");
            throw HumanResourceExceptionConstant.HUMAN_RESOURCE_NOT_FOUND;
        }
    }

    public void checkIfEmployeeAndHumanResourceMatch(HumanResource humanResource,Employee employee,ChangeDayOff changeDayOff) {
        if (Objects.equals(employee.getHumanResourceId(), humanResource.getId())) {
            employee.setDayOff(changeDayOff.getDayOff());
            log.info(employee.getName() + "'s day off has been changed to " + changeDayOff.getDayOff());
        } else {
            log.error("There is no such employee in human resources. Salary cannot be changed");
            throw HumanResourceExceptionConstant.CANNOT_CHANGE_DAY_OFF;
        }
    }

    public void checkIfEmployeeAndHumanResourceMatch(HumanResource humanResource, Employee employee, ChangeSalary changeSalary) {
        if (Objects.equals(employee.getHumanResourceId(), humanResource.getId())) {
            employee.setSalary(changeSalary.getSalary());
            log.info(employee.getName() + "'s salary has been changed to " + changeSalary.getSalary());
        } else {
            log.error("There is no such employee in human resources. Salary cannot be changed");
            throw HumanResourceExceptionConstant.CANNOT_CHANGE_DAY_OFF;
        }
    }

    public void checkIfHumanResourceNameExists(String name) {
        if (this.humanResourceRepository.existsByHumanResourceName(name)) {
            throw HumanResourceExceptionConstant.HUMAN_RESOURCE_NAME_EXISTS;
        }
    }
}
