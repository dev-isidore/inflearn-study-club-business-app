package org.example.demobusinessapp.controller.employee;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.demobusinessapp.domain.employee.Employee;
import org.example.demobusinessapp.dto.request.employee.EmployeeCreateRequest;
import org.example.demobusinessapp.dto.response.employee.EmployeeCreateResponse;
import org.example.demobusinessapp.dto.response.employee.EmployeeQueryResponse;
import org.example.demobusinessapp.service.employee.EmployeeService;
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
        if(employees == null) {
            return responses;
        }
        for (Employee employee : employees) {
            responses.add(new EmployeeQueryResponse(employee));
        }
        return responses;
    }
}
