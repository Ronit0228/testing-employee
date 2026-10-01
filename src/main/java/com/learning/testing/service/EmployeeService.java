package com.learning.testing.service;

import com.learning.testing.dto.EmployeeDto;
import com.learning.testing.dto.RequestEmployee;

public interface EmployeeService {

    EmployeeDto createEmployee(RequestEmployee requestEmployee);
    EmployeeDto findById(Long id);
    EmployeeDto findByEmail(String email);

}
