package com.sametcanal.business.abstracts;

import com.sametcanal.business.requests.create.CreateEmployeeRequest;
import com.sametcanal.business.requests.update.UpdateEmployeeRequest;
import com.sametcanal.entitites.concretes.Employee;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmployeeService {
    // Employee
    // List - Find By ID
    List<Employee> getEmployees();

    ResponseEntity<Employee> getEmployeeById(Long id);

    // Create - Update - Delete
    Employee createEmployee(CreateEmployeeRequest createEmployeeRequest);

    ResponseEntity<Employee> updateEmployee(UpdateEmployeeRequest updateEmployeeRequest);

    Boolean deleteEmployee(Long id);
}
