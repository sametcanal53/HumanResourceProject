package com.sametcanal.business.concretes;

import com.sametcanal.business.requests.create.CreateEmployeeRequest;
import com.sametcanal.business.requests.update.UpdateEmployeeRequest;
import com.sametcanal.core.utilities.exception.HumanResourceException;
import com.sametcanal.entitites.concretes.Employee;
import com.sametcanal.dataAccess.abstracts.EmployeeRepository;
import com.sametcanal.business.abstracts.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeManager implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getEmployees() {
        log.info("List of Employees");
        return this.employeeRepository.findAll();
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(Long id) {
        if (!employeeRepository.existsById(id)) {
            log.error("Employee Not Found! ");
            throw new HumanResourceException("HRP-2001", "Employee Not Found", HttpStatus.NOT_FOUND);
        }
        log.info("Employee Id : " + id);
        return ResponseEntity.ok(this.employeeRepository.findById(id).orElse(null));
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
        if (!employeeRepository.existsById(updateEmployeeRequest.getId())) {
            log.error("Employee Not Found! ");
            throw new HumanResourceException("HRP-2001", "Employee Not Found", HttpStatus.NOT_FOUND);
        }
        Employee updateEmployee = employeeRepository.findById(updateEmployeeRequest.getId()).orElse(null);
        updateEmployee.setName(updateEmployeeRequest.getName());
        updateEmployee.setSalary(updateEmployeeRequest.getSalary());
        updateEmployee.setDayOff(updateEmployeeRequest.getDayOff());
        updateEmployee.setHumanResourceId(updateEmployeeRequest.getHumanResourceId());
        log.info("Employee was successfully updated.");
        this.employeeRepository.save(updateEmployee);
        return ResponseEntity.ok(updateEmployee);
    }

    @Override
    public Boolean deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            log.error("Employee Not Found! ");
            throw new HumanResourceException("HRP-2001", "Employee Not Found", HttpStatus.NOT_FOUND);
        }
        log.info("Employee was successfully deleted.");
        this.employeeRepository.deleteById(id);
        return true;
    }
}
