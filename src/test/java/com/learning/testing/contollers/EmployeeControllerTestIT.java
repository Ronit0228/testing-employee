package com.learning.testing.contollers;

import com.learning.testing.TestContainerConfiguration;
import com.learning.testing.dto.EmployeeDto;
import com.learning.testing.dto.RequestEmployee;
import com.learning.testing.entity.Employee;
import com.learning.testing.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class EmployeeControllerTestIT extends AbstractIntegrationTest{

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee testEmployee;
    private RequestEmployee requestEmployeeMock;
    private EmployeeDto employeeDto;

    @BeforeEach
    void setUpEmployee() {
        employeeRepository.deleteAll();

        testEmployee = Employee.builder()
                .name("Ronit")
                .email("ronit@gmail.com")
                .salary(100000000000L)
                .build();

        requestEmployeeMock = RequestEmployee.builder()
                .name("Ronit")
                .email("ronit@gmail.com")
                .salary(100000000.0)
                .build();
    }

    @Test
    void testGetEmployeeId_success() {

        Employee savedEmployee = employeeRepository.save(testEmployee);

        EmployeeDto employeeDto = EmployeeDto.builder()
                .id(savedEmployee.getId())
                .name(savedEmployee.getName())
                .email(savedEmployee.getEmail())
                .salary(savedEmployee.getSalary())
                .build();

        webTestClient.get()
                .uri("/api/v1/employees/{id}", savedEmployee.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeDto.class)
                .isEqualTo(employeeDto);
    }

    @Test
    void testGetEmployeeById_failure() {
        webTestClient.get()
                .uri("/api/v1/employees/8")
                .exchange()
                .expectStatus().isNotFound();

    }

    @Test
    void testCreateNewEmployee_whenEmployeeAlreadyExist_thenThrowException() {
        employeeRepository.save(testEmployee);

        webTestClient.post()
                .uri("/api/v1/employees")
                .bodyValue(requestEmployeeMock)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void testCreateNewEmployee_whenEmployeeDoesNotExist_thenCreateEmployee() {
        webTestClient.post()
                .uri("/api/v1/employees")
                .bodyValue(requestEmployeeMock)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.email").isEqualTo(requestEmployeeMock.getEmail())
                .jsonPath("$.name").isEqualTo(requestEmployeeMock.getName());
    }

    @Test
    void testFindEmployeeByEmail_success() {

        Employee savedEmployee = employeeRepository.save(testEmployee);

        EmployeeDto employeeDto = EmployeeDto.builder()
                .id(savedEmployee.getId())
                .name(savedEmployee.getName())
                .email(savedEmployee.getEmail())
                .salary(savedEmployee.getSalary())
                .build();

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/employees/email")
                        .queryParam("email", savedEmployee.getEmail())
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeDto.class)
                .isEqualTo(employeeDto);
    }

    @Test
    void testFindEmployeeByEmail_failure() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/employees/email")
                        .queryParam("email", "vivek@gmail.com")
                        .build()
                )
                .exchange()
                .expectStatus().isNotFound();
    }

}