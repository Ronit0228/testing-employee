package com.learning.testing.contollers;

import com.learning.testing.dto.EmployeeDto;
import com.learning.testing.dto.RequestEmployee;
import com.learning.testing.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(
            @RequestBody RequestEmployee requestEmployee
    ) {
        EmployeeDto employeeDto = employeeService.createEmployee(requestEmployee);

        return new ResponseEntity<>(employeeDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> findById(
            @PathVariable Long id
    ) {
        EmployeeDto employeeDto = employeeService.findById(id);

        return ResponseEntity.ok(employeeDto);
    }

    @GetMapping("/email")
    public ResponseEntity<EmployeeDto> findByEmail(
            @RequestParam String email
    ) {
        EmployeeDto employeeDto = employeeService.findByEmail(email);

        return ResponseEntity.ok(employeeDto);
    }
}