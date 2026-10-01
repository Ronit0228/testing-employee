package com.learning.testing.repository;

import com.learning.testing.TestContainerConfiguration;
import com.learning.testing.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

//@SpringBootTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Import(TestContainerConfiguration.class)
@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUpEmployee() {
        employee = Employee.builder()
                .name("Ronit")
                .email("ronit@gmail.com")
                .salary(100000000000L)
                .build();
    }

    @Test
    void testFindByEmail_whenEmailIsPresent_thenReturnEmployee() {
        //Arrange, Given
        employeeRepository.save(employee);

        //Act, When
        Optional<Employee> employee1 = employeeRepository.findByEmail(employee.getEmail());

        //Assert, Then
        assertThat(employee1).isNotNull();
        assertThat(employee1).isNotEmpty();
    }

    @Test
    void testFindByEmail_whenEmailIsNotFound_thenReturnEmptyEmployeeList() {
        // Given
        String email = "notPresent12@gmail.com";

        //when
        Optional<Employee> employee1 = employeeRepository.findByEmail(email);

        //Then
        assertThat(employee1).isNotNull();
        assertThat(employee1).isEmpty();
    }
}