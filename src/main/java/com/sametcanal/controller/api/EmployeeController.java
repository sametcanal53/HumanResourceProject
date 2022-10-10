package com.sametcanal.controller.api;

import com.sametcanal.controller.request.EmployeeRequest;
import com.sametcanal.model.Employee;
import com.sametcanal.service.abstracts.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/")
    List<Employee> getEmployees(){
        return this.employeeService.getEmployees();
    }

    @GetMapping("/{id}")
    ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
        return this.employeeService.getEmployeeById(id);
    }

    // Create - Update - Delete
    @PostMapping("/")
    Employee createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest){
        return this.employeeService.createEmployee(employeeRequest);
    }

    @PatchMapping("/")
    ResponseEntity<Employee> updateEmployee(@Valid @RequestBody EmployeeRequest employeeRequest){
        return this.employeeService.updateEmployee(employeeRequest);
    }

    @DeleteMapping("/{id}")
    Boolean deleteEmployee(@PathVariable Long id){
        return this.employeeService.deleteEmployee(id);
    }
}
