package com.learning.testing.service.impl;

import com.learning.testing.TestContainerConfiguration;
import com.learning.testing.dto.EmployeeDto;
import com.learning.testing.dto.RequestEmployee;
import com.learning.testing.entity.Employee;
import com.learning.testing.exception.ResourceNotFoundException;
import com.learning.testing.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Import(TestContainerConfiguration.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee mockEmployee;
    private RequestEmployee requestEmployeeMock;

    @BeforeEach
    void setupEmployee() {
        mockEmployee = Employee.builder()
                .id(1L)
                .name("Ronit")
                .email("ronit@gmail.com")
                .salary(100000000.0)
                .build();

        requestEmployeeMock = RequestEmployee.builder()
                .name("Ronit")
                .email("ronit@gmail.com")
                .salary(100000000.0)
                .build();
    }

    @Test
    void testGetEmployeeById_whenEmployeeIsPresent_thenReturnEmployeeDto() {
        //assign
        Long id = mockEmployee.getId();
        when(employeeRepository.findById(id)).thenReturn(Optional.of(mockEmployee)); //Stubbing

        //act
        EmployeeDto employeeDto = employeeService.findById(id);

        //assert
        assertThat(employeeDto.getId()).isEqualTo(id);
        assertThat(employeeDto.getName()).isEqualTo(mockEmployee.getName());
        assertThat(employeeDto.getEmail()).isEqualTo(mockEmployee.getEmail());
        assertThat(employeeDto.getSalary()).isEqualTo(mockEmployee.getSalary());
//        verify(employeeRepository).findByEmail("amit@gmail.com");
//        verify(employeeRepository, times(2)).findByEmail("amit@gmail.com");
//        verify(employeeRepository, atLeast(1)).findById(id);
    }

    @Test
    void testGetEmployeeById_whenEmployeeIsNotPresent_thenThrowException(){
        //assign
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
        //act & assert
        assertThatThrownBy(() -> employeeService.findById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with id : 1");

        verify(employeeRepository).findById(1L);
    }

    @Test
    void testCreateEmployee_whenValidEmployee_thenCreateNewEmployee() {
        //assign
        when(employeeRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(employeeRepository.save(any(Employee.class))).thenReturn(mockEmployee);

        //act
        EmployeeDto employeeDto = employeeService.createEmployee(requestEmployeeMock);

        //assert
        assertThat(employeeDto).isNotNull();
        assertThat(employeeDto.getName()).isNotEmpty();
        assertThat(employeeDto.getEmail()).isNotEmpty();

        ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(employeeArgumentCaptor.capture());

        Employee capturedEmployee = employeeArgumentCaptor.getValue();
        assertThat(capturedEmployee.getEmail()).isEqualTo(mockEmployee.getEmail());
    }

    @Test
    void testCreateNewEmployee_whenAttemptingToCreateEmployeeWithExistingEmail_thenThrowException(){
        //assign
        when(employeeRepository.findByEmail(anyString())).thenReturn(Optional.of(mockEmployee));

        //act & assert
        assertThatThrownBy(() -> employeeService.createEmployee(requestEmployeeMock))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Employee already exists with email: ronit@gmail.com");

        verify(employeeRepository).findByEmail("ronit@gmail.com");
    }

    @Test
    void testFindByEmail_whenEmailIsExist_thenReturnEmployeeDto() {
        //assign
        String email = mockEmployee.getEmail();
        when(employeeRepository.findByEmail(email)).thenReturn(Optional.of(mockEmployee));

        //act
        EmployeeDto employeeDto = employeeService.findByEmail(email);

        //assert
        assertThat(employeeDto).isNotNull();
        assertThat(employeeDto.getId()).isEqualTo(mockEmployee.getId());
        assertThat(employeeDto.getEmail()).isEqualTo(mockEmployee.getEmail());
        assertThat(employeeDto.getName()).isEqualTo(mockEmployee.getName());
        assertThat(employeeDto.getSalary()).isEqualTo(mockEmployee.getSalary());
    }

    @Test
    void testFindByEmail_whenEmailIsNotPresent_thenThrowException() {
        //assign
        when(employeeRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        //act & assign
        assertThatThrownBy(() -> employeeService.findByEmail(anyString()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with email : ");
    }
}