package org.example.demobusinessapp.employee.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.demobusinessapp.employee.domain.Employee;
import org.example.demobusinessapp.employee.dto.request.EmployeeCreateRequest;
import org.example.demobusinessapp.employee.dto.response.EmployeeCreateResponse;
import org.example.demobusinessapp.employee.service.EmployeeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmployeeApiController {
    private final EmployeeService employeeService;

    @PostMapping("/api/v1/employee")
    public EmployeeCreateResponse createMember(@RequestBody @Valid EmployeeCreateRequest request) {
        Employee employee = employeeService.createEmployee(request);
        return new EmployeeCreateResponse(employee);
    }
}
