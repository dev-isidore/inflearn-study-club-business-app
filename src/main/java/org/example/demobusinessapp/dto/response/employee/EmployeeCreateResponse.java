package org.example.demobusinessapp.dto.response.employee;

import lombok.Getter;
import org.example.demobusinessapp.domain.employee.Employee;
import org.example.demobusinessapp.domain.employee.Role;

import java.time.LocalDate;

@Getter
public class EmployeeCreateResponse {
    private final String name;

    private final String teamName;

    private final Role role;

    private final LocalDate birthday;

    private final LocalDate workStartDate;

    public EmployeeCreateResponse(Employee employee) {
        this.name = employee.getName();
        this.teamName = employee.getTeam().getName();
        this.role = employee.getRole();
        this.birthday = employee.getBirthday();
        this.workStartDate = employee.getWorkStartDate();
    }
}
