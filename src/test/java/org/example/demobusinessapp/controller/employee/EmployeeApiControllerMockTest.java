package org.example.demobusinessapp.controller.employee;

import org.example.demobusinessapp.controller.employee.EmployeeApiController;
import org.example.demobusinessapp.domain.employee.Employee;
import org.example.demobusinessapp.dto.request.employee.EmployeeCreateRequest;
import org.example.demobusinessapp.dto.response.employee.EmployeeCreateResponse;
import org.example.demobusinessapp.dto.response.employee.EmployeeQueryResponse;
import org.example.demobusinessapp.service.employee.EmployeeService;
import org.example.demobusinessapp.domain.team.Team;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeApiControllerMockTest {
    private static final String TEST_EMPLOYEE_NAME = "John";

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeApiController employeeApiController;

    @Test
    void createEmployee() {
        when(employeeService.createEmployee(any())).thenReturn(Employee.builder().name(TEST_EMPLOYEE_NAME).team(new Team("dev")).build());

        EmployeeCreateResponse response = employeeApiController.createEmployee(new EmployeeCreateRequest());

        assertThat(response.getName()).isEqualTo(TEST_EMPLOYEE_NAME);
    }

    @Test
    void getEmployees() {
        when(employeeService.getEmployees()).thenReturn(List.of(Employee.builder().name(TEST_EMPLOYEE_NAME).team(new Team("dev")).build()));

        List<EmployeeQueryResponse> responseList = employeeApiController.getEmployees();

        assertThat(responseList.size()).isEqualTo(1);
    }
}