package org.example.demobusinessapp.employee.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.demobusinessapp.employee.domain.Employee;
import org.example.demobusinessapp.employee.dto.request.EmployeeCreateRequest;
import org.example.demobusinessapp.employee.dto.response.EmployeeCreateResponse;
import org.example.demobusinessapp.employee.dto.response.EmployeeQueryResponse;
import org.example.demobusinessapp.employee.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeApiController {
    private final EmployeeService employeeService;

    @PostMapping("/api/v1/employee")
    public EmployeeCreateResponse createEmployee(@RequestBody @Valid EmployeeCreateRequest request) {
        Employee employee = employeeService.createEmployee(request);
        return new EmployeeCreateResponse(employee);
    }

    @GetMapping("/api/v1/employees")
    public List<EmployeeQueryResponse> getEmployees() {
        List<EmployeeQueryResponse> responses = new ArrayList<>();
        List<Employee> employees = employeeService.getEmployees();
        for (Employee employee : employees) {
            responses.add(new EmployeeQueryResponse(employee));
        }
        return responses;
    }
}
