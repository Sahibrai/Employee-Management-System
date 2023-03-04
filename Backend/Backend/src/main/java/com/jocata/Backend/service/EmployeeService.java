package com.jocata.Backend.service;

import com.jocata.Backend.entity.Employee;
import com.jocata.Backend.request.EmployeeRequest;
import com.jocata.Backend.response.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    public Employee save(EmployeeRequest employeeRequest);
    public Employee updateEmployee(Long id, EmployeeRequest employeeRequest);
    public List<EmployeeResponse> getEmployeeList();
    public Employee getEmployeeById(Long id);
    public void deleteEmployeeById(Long id);
}
