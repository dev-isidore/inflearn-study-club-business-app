package org.example.demobusinessapp.employee.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.example.demobusinessapp.employee.domain.Role;

import java.time.LocalDate;

@Getter
public class EmployeeCreateRequest {

    @NotBlank
    private String name;

    private String teamName;

    private Role role;

    private LocalDate birthday;

    private LocalDate workStartDate;
}
