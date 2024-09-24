package net.javaguides.springboot.controller;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeControllerTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L, "Pratiksha", "Bhende", "pratikshabhende@gmail.com"));

        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> result = employeeController.getAllEmployees();

        assertEquals(1, result.size());
        assertEquals("Pratiksha", result.get(0).getFirstName());

        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void createEmployee() {
        Employee employeeToCreate = new Employee(1L, "Pratiksha", "Bhende", "pratikshabhende@gmail.com");

        when(employeeRepository.save(any(Employee.class))).thenReturn(employeeToCreate);

        Employee createdEmployee = employeeController.createEmployee(employeeToCreate);

        assertNotNull(createdEmployee);
        assertEquals("Pratiksha", createdEmployee.getFirstName());

        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void getEmployeeById() {
        long employeeId = 1L;
        Employee employee = new Employee(employeeId, "Pratiksha", "Bhende", "pratikshabhende@gmail.com");

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        ResponseEntity<Employee> responseEntity = employeeController.getEmployeeById(employeeId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Pratiksha", responseEntity.getBody().getFirstName());

        verify(employeeRepository, times(1)).findById(employeeId);
    }

    @Test
    void getEmployeeById_ThrowsException() {
        long nonExistentEmployeeId = 99L;

        when(employeeRepository.findById(nonExistentEmployeeId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> employeeController.getEmployeeById(nonExistentEmployeeId));

        verify(employeeRepository, times(1)).findById(nonExistentEmployeeId);
    }

    @Test
    void updateEmployee() {
        long employeeId = 1L;
        Employee existingEmployee = new Employee(employeeId, "Pratiksha", "Bhende", "pratikshabhende@gmail.com");
        Employee updatedEmployeeDetails = new Employee(employeeId, "Pratiksha", "Bhende", "pratikshab14@gmail.com");

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployeeDetails);

        ResponseEntity<Employee> responseEntity =
                employeeController.updateEmployee(employeeId, updatedEmployeeDetails);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Pratiksha", responseEntity.getBody().getFirstName());

        verify(employeeRepository, times(1)).findById(employeeId);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void updateEmployee_ThrowsException() {
        long nonExistentEmployeeId = 99L;
        Employee updatedEmployeeDetails = new Employee(nonExistentEmployeeId, "Pratiksha", "Bhende", "pratikshabhende@gmail.com");

        when(employeeRepository.findById(nonExistentEmployeeId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> employeeController.updateEmployee(nonExistentEmployeeId, updatedEmployeeDetails));

        verify(employeeRepository, times(1)).findById(nonExistentEmployeeId);
        verify(employeeRepository, times(0)).save(any(Employee.class));
    }

    @Test
    void deleteEmployee() {
        long employeeId = 1L;
        Employee existingEmployee = new Employee(employeeId, "Pratiksha", "Bhende", "pratikshabhende@gmail.com");

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));

        ResponseEntity<HttpStatus> responseEntity = employeeController.deleteEmployee(employeeId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        verify(employeeRepository, times(1)).findById(employeeId);
        verify(employeeRepository, times(1)).delete(existingEmployee);
    }

    @Test
    void deleteEmployee_ThrowsException() {
        long nonExistentEmployeeId = 99L;

        when(employeeRepository.findById(nonExistentEmployeeId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> employeeController.deleteEmployee(nonExistentEmployeeId));

        verify(employeeRepository, times(1)).findById(nonExistentEmployeeId);
        verify(employeeRepository, times(0)).delete(any(Employee.class));
    }
}
