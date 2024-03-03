package org.example.demobusinessapp.team.domain;

import org.example.demobusinessapp.employee.domain.Employee;
import org.example.demobusinessapp.employee.domain.Role;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class TeamTest {
    @Test
    void getManager() {
        Team team = new Team();
        List<Employee> employees = team.getEmployees();
        employees.add(Employee.builder().role(Role.MEMBER).build());
        employees.add(Employee.builder().name("managerName").role(Role.MANAGER).build());

        Optional<Employee> result = team.getManager();

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getName()).isEqualTo("managerName");
    }

    @Test
    void getManager_empty() {
        Team team = new Team("dev");
        Optional<Employee> result = team.getManager();

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void getManager_no_manager() {
        Team team = new Team();
        List<Employee> employees = team.getEmployees();
        employees.add(Employee.builder().role(Role.MEMBER).build());
        employees.add(Employee.builder().role(Role.MEMBER).build());

        Optional<Employee> result = team.getManager();

        assertThat(result.isEmpty()).isTrue();
    }
}