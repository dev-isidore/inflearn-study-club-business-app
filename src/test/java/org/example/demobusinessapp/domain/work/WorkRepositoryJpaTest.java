package org.example.demobusinessapp.domain.work;

import org.example.demobusinessapp.domain.employee.Employee;
import org.example.demobusinessapp.domain.employee.EmployeeRepository;
import org.example.demobusinessapp.domain.employee.Role;
import org.example.demobusinessapp.domain.team.Team;
import org.example.demobusinessapp.domain.team.TeamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class WorkRepositoryJpaTest {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private WorkRepository workRepository;

    @Test
    void findWorksByEmployeeAndStatusAndStartTimeIsBetween() {
        Team team = teamRepository.save(new Team("DEV"));
        Employee employee = employeeRepository.save(Employee.builder()
                .name("John")
                .team(team)
                .role(Role.MEMBER)
                .birthday(LocalDate.of(2000, 1, 1))
                .workStartDate(LocalDate.of(2024, 1, 1)).build());
        Work work1 = new Work(employee, LocalDateTime.of(2024, 1, 1, 10, 0));
        Work savedWork = workRepository.save(work1);

        {
            List<Work> works = workRepository.findWorksByEmployeeAndStatusAndStartTimeIsBetween(employee, Status.OFF, LocalDateTime.of(LocalDate.of(2024, 1, 1), LocalTime.MIN), LocalDateTime.of(LocalDate.of(2024, 1, 31), LocalTime.MAX));
            assertThat(works.size()).isEqualTo(0);
        }
        {
            savedWork.off(LocalDateTime.of(2024, 1, 1, 19, 0));
            workRepository.save(savedWork);
            List<Work> works = workRepository.findWorksByEmployeeAndStatusAndStartTimeIsBetween(employee, Status.OFF, LocalDateTime.of(LocalDate.of(2024, 1, 1), LocalTime.MIN), LocalDateTime.of(LocalDate.of(2024, 1, 31), LocalTime.MAX));
            assertThat(works.size()).isEqualTo(1);
        }
    }
    @Test
    void findWorksByEmployeeAndStatusAndStartTimeIsBetween_edge_case() {
        Team team = teamRepository.save(new Team("DEV"));
        Employee employee = employeeRepository.save(Employee.builder()
                .name("John")
                .team(team)
                .role(Role.MEMBER)
                .birthday(LocalDate.of(2000, 1, 1))
                .workStartDate(LocalDate.of(2024, 1, 1)).build());
        Work work1 = new Work(employee, LocalDateTime.of(2024, 1, 1, 10, 0));
        Work savedWork = workRepository.save(work1);

        {
            List<Work> works = workRepository.findWorksByEmployeeAndStatusAndStartTimeIsBetween(employee, Status.OFF, LocalDateTime.of(2024, 1, 1, 10, 0), LocalDateTime.of(LocalDate.of(2024, 1, 31), LocalTime.MAX));
            assertThat(works.size()).isEqualTo(0);
        }
        {
            savedWork.off(LocalDateTime.of(2024, 1, 1, 19, 0));
            workRepository.save(savedWork);
            List<Work> works = workRepository.findWorksByEmployeeAndStatusAndStartTimeIsBetween(employee, Status.OFF, LocalDateTime.of(LocalDate.of(2024, 1, 1), LocalTime.MIN), LocalDateTime.of(LocalDate.of(2024, 1, 31), LocalTime.MAX));
            assertThat(works.size()).isEqualTo(1);
        }
    }
}
