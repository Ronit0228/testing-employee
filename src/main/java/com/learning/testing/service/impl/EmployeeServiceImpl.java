package com.learning.testing.service.impl;

import com.learning.testing.dto.EmployeeDto;
import com.learning.testing.dto.RequestEmployee;
import com.learning.testing.entity.Employee;
import com.learning.testing.exception.ResourceNotFoundException;
import com.learning.testing.repository.EmployeeRepository;
import com.learning.testing.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto createEmployee(RequestEmployee requestEmployee) {
        Optional<Employee> existEmployee = employeeRepository.findByEmail(requestEmployee.getEmail());
        if(existEmployee.isPresent()) {
            throw new RuntimeException("Employee already exists with email: " + requestEmployee.getEmail());
        }
        log.info("Employee is creating");
        Employee employee = Employee.builder()
                .name(requestEmployee.getName())
                .email(requestEmployee.getEmail())
                .salary(requestEmployee.getSalary())
                .build();

        log.info("Employee is created with id : {}", employee.getId());
        employee = employeeRepository.save(employee);

        return EmployeeDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .email(employee.getEmail())
                .salary(employee.getSalary())
                .build();
    }

    @Override
    public EmployeeDto findById(Long id) {
        log.info("Finding the employee with id : {}", id);
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee not found with id : " + id)
        );
        log.info("Found the employee with id : {}", id);
        return EmployeeDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .email(employee.getEmail())
                .salary(employee.getSalary())
                .build();
    }

    @Override
    public EmployeeDto findByEmail(String email) {
        log.info("Finding the employee with email : {}", email);
        Employee employee = employeeRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Employee not found with email : " + email)
        );
        log.info("Found the employee with email : {}", email);
        return EmployeeDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .email(employee.getEmail())
                .salary(employee.getSalary())
                .build();
    }
}
