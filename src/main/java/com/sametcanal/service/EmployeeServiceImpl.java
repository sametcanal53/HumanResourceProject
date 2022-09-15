package com.sametcanal.service;

import com.sametcanal.controller.request.EmployeeRequest;
import com.sametcanal.exception.HumanResourceException;
import com.sametcanal.model.Employee;
import com.sametcanal.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getEmployees() {
        return this.employeeRepository.findAll();
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new HumanResourceException("HRP-2001", "Employee Not Found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(this.employeeRepository.findById(id).orElse(null));
    }

    @Override
    public Employee createEmployee(EmployeeRequest employeeRequest) {
        Employee employee = Employee
                .builder()
                .name(employeeRequest.getName())
                .salary(employeeRequest.getSalary())
                .dayOff(employeeRequest.getDayOff())
                .humanResourceId(employeeRequest.getHumanResourceId())
                .build();
        return this.employeeRepository.save(employee);
    }

    @Override
    public ResponseEntity<Employee> updateEmployee(EmployeeRequest employeeRequest) {
        if (!employeeRepository.existsById(employeeRequest.getId())) {
            throw new HumanResourceException("HRP-2001", "Employee Not Found", HttpStatus.NOT_FOUND);
        }
        Employee updateEmployee = employeeRepository.findById(employeeRequest.getId()).orElse(null);
        updateEmployee.setName(employeeRequest.getName());
        updateEmployee.setSalary(employeeRequest.getSalary());
        updateEmployee.setDayOff(employeeRequest.getDayOff());
        updateEmployee.setHumanResourceId(employeeRequest.getHumanResourceId());
        this.employeeRepository.save(updateEmployee);
        return ResponseEntity.ok(updateEmployee);
    }

    @Override
    public Boolean deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new HumanResourceException("HRP-2001", "Employee Not Found", HttpStatus.NOT_FOUND);
        }
        this.employeeRepository.deleteById(id);
        return true;
    }
}
