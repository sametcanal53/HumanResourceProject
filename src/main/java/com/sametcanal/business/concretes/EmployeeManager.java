package com.sametcanal.business.concretes;

import com.sametcanal.business.requests.create.CreateEmployeeRequest;
import com.sametcanal.business.requests.update.UpdateEmployeeRequest;
import com.sametcanal.business.rules.EmployeeBusinessRules;
import com.sametcanal.core.utilities.exception.HumanResourceException;
import com.sametcanal.entitites.concretes.Employee;
import com.sametcanal.dataAccess.abstracts.EmployeeRepository;
import com.sametcanal.business.abstracts.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class EmployeeManager implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private EmployeeBusinessRules employeeBusinessRules;

    @Override
    public List<Employee> getEmployees() {
        log.info("List of Employees");
        return this.employeeRepository.findAll();
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(Long id) {
        this.employeeBusinessRules.checkIfEmployeeExists(id);
        log.info("Employee Id : " + id);
        return ResponseEntity.ok().body(this.employeeRepository.findById(id).orElse(null));
    }

    @Override
    public Employee createEmployee(CreateEmployeeRequest createEmployeeRequest) {
        Employee employee = Employee
                .builder()
                .name(createEmployeeRequest.getName())
                .salary(createEmployeeRequest.getSalary())
                .dayOff(createEmployeeRequest.getDayOff())
                .humanResourceId(createEmployeeRequest.getHumanResourceId())
                .build();
        log.info("Employee was successfully created.");
        return this.employeeRepository.save(employee);
    }

    @Override
    public ResponseEntity<Employee> updateEmployee(UpdateEmployeeRequest updateEmployeeRequest){
        this.employeeBusinessRules.checkIfEmployeeExists(updateEmployeeRequest.getId());
        Employee updateEmployee = employeeRepository.findById(updateEmployeeRequest.getId()).orElse(null);
        updateEmployee.setName(updateEmployeeRequest.getName());
        updateEmployee.setSalary(updateEmployeeRequest.getSalary());
        updateEmployee.setDayOff(updateEmployeeRequest.getDayOff());
        updateEmployee.setHumanResourceId(updateEmployeeRequest.getHumanResourceId());
        log.info("Employee was successfully updated.");
        this.employeeRepository.save(updateEmployee);
        return ResponseEntity.ok().body(updateEmployee);
    }

    @Override
    public Boolean deleteEmployee(Long id) {
        this.employeeBusinessRules.checkIfEmployeeExists(id);
        log.info("Employee was successfully deleted.");
        this.employeeRepository.deleteById(id);
        return true;
    }
}
