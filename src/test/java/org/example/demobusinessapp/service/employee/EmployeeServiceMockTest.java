package org.example.demobusinessapp.service.employee;

import org.example.demobusinessapp.domain.employee.Employee;
import org.example.demobusinessapp.domain.employee.EmployeeRepository;
import org.example.demobusinessapp.domain.employee.Role;
import org.example.demobusinessapp.dto.request.employee.EmployeeCreateRequest;
import org.example.demobusinessapp.service.employee.EmployeeService;
import org.example.demobusinessapp.domain.team.Team;
import org.example.demobusinessapp.domain.team.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceMockTest {

    private static final String TEST_EMPLOYEE_NAME = "John";
    private static final String TEST_TEAM_NAME = "dev";
    private static final LocalDate TEST_EMPLOYEE_BDAY = LocalDate.of(2000, 1, 1);
    private static final LocalDate TEST_EMPLOYEE_WDAY = LocalDate.of(2024, 1, 1);

    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    TeamRepository teamRepository;

    @InjectMocks
    EmployeeService employeeService;

    @Test
    void createEmployee() {
        EmployeeCreateRequest request = mock(EmployeeCreateRequest.class);
        when(request.getName()).thenReturn(TEST_EMPLOYEE_NAME);
        when(request.getBirthday()).thenReturn(TEST_EMPLOYEE_BDAY);
        when(request.getWorkStartDate()).thenReturn(TEST_EMPLOYEE_WDAY);
        when(request.getTeamName()).thenReturn(TEST_TEAM_NAME);

        Team team = new Team(TEST_TEAM_NAME);
        when(teamRepository.findByName(eq(TEST_TEAM_NAME))).thenReturn(Optional.of(team));

        when(request.getRole()).thenReturn(Role.MEMBER);

        ArgumentCaptor<Employee> argumentCaptor = ArgumentCaptor.forClass(Employee.class);

        employeeService.createEmployee(request);

        verify(employeeRepository).save(argumentCaptor.capture());
        Employee savedEmployee = argumentCaptor.getValue();
        assertThat(savedEmployee.getName()).isEqualTo(TEST_EMPLOYEE_NAME);
        assertThat(savedEmployee.getBirthday()).isEqualTo(TEST_EMPLOYEE_BDAY);
    }

    @Test
    void createEmployee_wrong_teamName() {
        EmployeeCreateRequest request = mock(EmployeeCreateRequest.class);
        when(request.getName()).thenReturn(TEST_EMPLOYEE_NAME);
        when(request.getBirthday()).thenReturn(TEST_EMPLOYEE_BDAY);
        when(request.getWorkStartDate()).thenReturn(TEST_EMPLOYEE_WDAY);
        when(request.getTeamName()).thenReturn("wrongName");

        assertThrows(IllegalArgumentException.class, ()-> employeeService.createEmployee(request));
    }

    @Test
    void createEmployee_set_manager_at_team_with_manager() {
        EmployeeCreateRequest request = mock(EmployeeCreateRequest.class);
        when(request.getName()).thenReturn(TEST_EMPLOYEE_NAME);
        when(request.getBirthday()).thenReturn(TEST_EMPLOYEE_BDAY);
        when(request.getWorkStartDate()).thenReturn(TEST_EMPLOYEE_WDAY);
        when(request.getTeamName()).thenReturn(TEST_TEAM_NAME);

        Team team = mock(Team.class);
        when(teamRepository.findByName(eq(TEST_TEAM_NAME))).thenReturn(Optional.of(team));

        when(request.getRole()).thenReturn(Role.MANAGER);
        when(team.getManager()).thenReturn(Optional.of(Employee.builder().build()));

        assertThrows(IllegalArgumentException.class, ()-> employeeService.createEmployee(request));
    }

    @Test
    void getEmployees() {
        when(employeeRepository.findAll()).thenReturn(List.of(
                Employee.builder().build()
        ));

        List<Employee> result = employeeService.getEmployees();

        assertThat(result.size()).isEqualTo(1);
    }
}