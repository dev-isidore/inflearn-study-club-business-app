package org.example.demobusinessapp.dto.request.employee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.example.demobusinessapp.domain.employee.Role;

import java.time.LocalDate;

@Getter
public class EmployeeCreateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String teamName;

    @NotNull
    private Role role;

    @NotNull
    private LocalDate birthday;

    @NotNull
    private LocalDate workStartDate;
}
