package com.sametcanal.webApi.controllers;

import com.sametcanal.business.requests.create.CreateEmployeeRequest;
import com.sametcanal.business.requests.update.UpdateEmployeeRequest;
import com.sametcanal.entitites.concretes.Employee;
import com.sametcanal.business.abstracts.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('HUMAN_RESOURCE','ADMIN')")
public class EmployeeController {

    private final EmployeeService employeeService;
    @GetMapping("/")
    List<Employee> getEmployees(){
        return this.employeeService.getEmployees();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('HUMAN_RESOURCE','ADMIN','EMPLOYEE')")
    ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
        return this.employeeService.getEmployeeById(id);
    }

    // Create - Update - Delete
    @PostMapping("/")
    Employee createEmployee(@Valid @RequestBody CreateEmployeeRequest createEmployeeRequest){
        return this.employeeService.createEmployee(createEmployeeRequest);
    }

    @PatchMapping("/")
    ResponseEntity<Employee> updateEmployee(@Valid @RequestBody UpdateEmployeeRequest employeeRequest){
        return this.employeeService.updateEmployee(employeeRequest);
    }

    @DeleteMapping("/{id}")
    Boolean deleteEmployee(@PathVariable Long id){
        return this.employeeService.deleteEmployee(id);
    }
}
