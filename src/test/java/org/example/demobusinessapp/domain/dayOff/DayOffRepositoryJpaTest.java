package org.example.demobusinessapp.domain.dayOff;

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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class DayOffRepositoryJpaTest {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DayOffRepository dayOffRepository;

    @Test
    void findDayOffsByEmployeeAndDateBetween(){
        Team team = new Team("DEV");
        team.setDayOffBuffer(10);
        team = teamRepository.save(team);

        Employee employee = employeeRepository.save(Employee.builder()
                .name("John")
                .team(team)
                .role(Role.MEMBER)
                .birthday(LocalDate.of(2000, 1, 1))
                .workStartDate(LocalDate.of(2024, 1, 1)).build());
        employeeRepository.save(employee);

        {
            List<DayOff> results = dayOffRepository.findDayOffsByEmployeeAndDateBetween(employee, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31));
            assertThat(results.isEmpty()).isTrue();
        }

        {
            DayOff dayOff = employee.goDayOff(Collections.emptyList(), LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 1));
            dayOffRepository.save(dayOff);

            List<DayOff> results = dayOffRepository.findDayOffsByEmployeeAndDateBetween(employee, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31));

            assertThat(results.size()).isEqualTo(1);
        }

        {
            DayOff dayOff = employee.goDayOff(Collections.emptyList(), LocalDate.of(2024, 1, 31), LocalDate.of(2024, 1, 22));
            dayOffRepository.save(dayOff);

            List<DayOff> results = dayOffRepository.findDayOffsByEmployeeAndDateBetween(employee, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31));

            assertThat(results.size()).isEqualTo(2);
        }
    }
}
