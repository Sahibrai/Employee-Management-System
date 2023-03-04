package com.jocata.Backend.service.impl;

import com.jocata.Backend.dao.EmployeeRepository;
import com.jocata.Backend.entity.Employee;
import com.jocata.Backend.exception.EmployeeNotFoundException;
import com.jocata.Backend.request.EmployeeRequest;
import com.jocata.Backend.response.EmployeeResponse;
import com.jocata.Backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee save(EmployeeRequest request) {
        validate(request);
        Employee employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setAge(request.getAge());
        employee.setEmail(request.getEmail());
        employee.setMobileNumber(request.getMobileNumber());

        return employeeRepository.save(employee);
    }
    @Override
    public List<EmployeeResponse> getEmployeeList() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(employee -> new EmployeeResponse(employee.getId(),
                        employee.getFirstName(), employee.getLastName(),
                        employee.getAge(), employee.getEmail(), employee.getMobileNumber()))
                .collect(Collectors.toList());
    }
    @Override
    public Employee getEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            return employee.get();
        } else {
            throw new EmployeeNotFoundException(id);
        }
    }

    @Override
    public void deleteEmployeeById(Long id) {
        employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee updateEmployee(Long id, EmployeeRequest employeeRequest) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (!optionalEmployee.isPresent()) {
            throw new IllegalArgumentException("Employee not found with id: " + id);
        }
        Employee employee = optionalEmployee.get();
        if (employeeRequest.getFirstName() != null) {
            employee.setFirstName(employeeRequest.getFirstName());
        }
        if (employeeRequest.getLastName() != null) {
            employee.setLastName(employeeRequest.getLastName());
        }
        if (employeeRequest.getAge() != 0) {
            employee.setAge(employeeRequest.getAge());
        }
        if (employeeRequest.getEmail() != null) {
            employee.setEmail(employeeRequest.getEmail());
        }
        if (employeeRequest.getMobileNumber() != null) {
            employee.setMobileNumber(employeeRequest.getMobileNumber());
        }
        return employeeRepository.save(employee);
    }


    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    private void validate(EmployeeRequest request) {
        if (StringUtils.isEmpty(request.getFirstName())) {
            throw new IllegalArgumentException("First name is mandatory");
        }
        if (StringUtils.isEmpty(request.getLastName())) {
            throw new IllegalArgumentException("Last name is mandatory");
        }
        if (Objects.isNull(request.getAge())) {
            throw new IllegalArgumentException("Age is mandatory");
        }
        if (StringUtils.isEmpty(request.getEmail())) {
            throw new IllegalArgumentException("Email is mandatory");
        }
        if (!request.getEmail().matches("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$")) {
            throw new IllegalArgumentException("Email is not valid");
        }
        if (StringUtils.isEmpty(request.getMobileNumber())) {
            throw new IllegalArgumentException("Mobile number is mandatory");
        }
        if (!request.getMobileNumber().matches("(^$|[0-9]{10})")) {
            throw new IllegalArgumentException("Mobile number is not valid");
        }
    }
}
