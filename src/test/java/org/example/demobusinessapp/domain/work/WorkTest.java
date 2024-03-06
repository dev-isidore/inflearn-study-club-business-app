package org.example.demobusinessapp.domain.work;

import org.example.demobusinessapp.domain.employee.Employee;
import org.example.demobusinessapp.domain.team.Team;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WorkTest {
    private static final Long TEST_EMPLOYEE_ID = 1L;
    private static final String TEST_EMPLOYEE_NAME = "John";
    private static final Team TEST_TEAM = new Team("dev");
    private static final Employee TEST_EMPLOYEE = Employee.builder()
            .id(TEST_EMPLOYEE_ID)
            .name(TEST_EMPLOYEE_NAME)
            .team(TEST_TEAM)
            .workStartDate(LocalDate.of(2024, 1, 1))
            .build();

    private static final LocalDateTime TEST_DATE_TIME = LocalDateTime.of(2024, 3, 6, 10, 0);
    @Test
    void off() {
        Work work = new Work(TEST_EMPLOYEE, TEST_DATE_TIME);
        final LocalDateTime offTime = TEST_DATE_TIME.plusHours(8);

        work.off(offTime);

        assertThat(work.getEndTime()).isEqualTo(offTime);
        assertThat(work.getStatus()).isEqualTo(Status.OFF);
    }

    @Test
    void off_again() {
        Work work = new Work(TEST_EMPLOYEE, TEST_DATE_TIME);

        work.off(TEST_DATE_TIME.plusHours(4));

        assertThrows(IllegalArgumentException.class, () -> work.off(TEST_DATE_TIME.plusHours(8)));
    }
}
