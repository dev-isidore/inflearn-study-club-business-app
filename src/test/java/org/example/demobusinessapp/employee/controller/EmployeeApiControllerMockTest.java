package org.example.demobusinessapp.employee.controller;

import org.example.demobusinessapp.employee.domain.Employee;
import org.example.demobusinessapp.employee.dto.request.EmployeeCreateRequest;
import org.example.demobusinessapp.employee.dto.response.EmployeeCreateResponse;
import org.example.demobusinessapp.employee.dto.response.EmployeeQueryResponse;
import org.example.demobusinessapp.employee.service.EmployeeService;
import org.example.demobusinessapp.team.domain.Team;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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