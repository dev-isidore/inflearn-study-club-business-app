package org.example.demobusinessapp.employee.dto.response;

import lombok.Getter;
import org.example.demobusinessapp.employee.domain.Employee;
import org.example.demobusinessapp.employee.domain.Role;

import java.time.LocalDate;

@Getter
public class EmployeeCreateResponse {
    private String name;

    private String teamName;

    private Role role;

    private LocalDate birthday;

    private LocalDate workStartDate;

    public EmployeeCreateResponse(Employee employee) {
        this.name = employee.getName();
        if(employee.getTeam() != null) {
            this.teamName = employee.getTeam().getName();
        }
        this.role = employee.getRole();
        this.birthday = employee.getBirthday();
        this.workStartDate = employee.getWorkStartDate();
    }
}
