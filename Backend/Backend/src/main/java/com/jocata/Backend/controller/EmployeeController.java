package com.jocata.Backend.controller;

import com.jocata.Backend.entity.Employee;
import com.jocata.Backend.request.EmployeeRequest;
import com.jocata.Backend.response.EmployeeResponse;
import com.jocata.Backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/v1/")
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employees")
    public ResponseEntity<EmployeeResponse> saveEmployee(@RequestBody EmployeeRequest employeeRequest) {
        Employee employee = employeeService.save(employeeRequest);
        EmployeeResponse response = new EmployeeResponse();
        response.setId(employee.getId());
        response.setFirstName(employee.getFirstName());
        response.setLastName(employee.getLastName());
        response.setAge(employee.getAge());
        response.setEmail(employee.getEmail());
        response.setMobileNumber(employee.getMobileNumber());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Long id, @Validated @RequestBody EmployeeRequest employeeRequest) {
        Employee employee = employeeService.updateEmployee(id, employeeRequest);
        EmployeeResponse employeeResponse = new EmployeeResponse(employee.getId(), employee.getFirstName(),
                employee.getLastName(), employee.getAge(), employee.getEmail(), employee.getMobileNumber());
        return ResponseEntity.ok(employeeResponse);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeResponse>> getEmployeeList() {
        List<EmployeeResponse> employeeList = employeeService.getEmployeeList();
        return ResponseEntity.ok(employeeList);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        EmployeeResponse employeeResponse = new EmployeeResponse(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getAge(),
                employee.getEmail(),
                employee.getMobileNumber());
        return ResponseEntity.ok(employeeResponse);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}