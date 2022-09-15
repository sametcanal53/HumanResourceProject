package com.sametcanal.service;

import com.sametcanal.controller.request.EmployeeRequest;
import com.sametcanal.model.Employee;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmployeeService {
    // Employee
    // List - Find By ID
    List<Employee> getEmployees();

    ResponseEntity<Employee> getEmployeeById(Long id);

    // Create - Update - Delete
    Employee createEmployee(EmployeeRequest employeeRequest);

    ResponseEntity<Employee> updateEmployee(EmployeeRequest employeeRequest);

    Boolean deleteEmployee(Long id);
}
