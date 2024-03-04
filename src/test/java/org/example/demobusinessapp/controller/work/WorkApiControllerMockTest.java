package org.example.demobusinessapp.controller.work;

import org.example.demobusinessapp.domain.employee.Employee;
import org.example.demobusinessapp.domain.work.Work;
import org.example.demobusinessapp.dto.response.work.MonthlyWorkResponse;
import org.example.demobusinessapp.dto.response.work.WorkOffResponse;
import org.example.demobusinessapp.dto.response.work.WorkOnResponse;
import org.example.demobusinessapp.service.work.WorkService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WorkApiControllerMockTest {
    private static final Long TEST_EMPLOYEE_ID = 1L;

    @Mock
    private WorkService workService;

    @InjectMocks
    private WorkApiController workApiController;

    @Test
    void putOnWork() {
        Work work = new Work(Employee.builder()
                .id(TEST_EMPLOYEE_ID)
                .build(), LocalDateTime.now()
        );
        when(workService.goOnWork(eq(TEST_EMPLOYEE_ID), any())).thenReturn(work);

        WorkOnResponse response = workApiController.putOnWork(TEST_EMPLOYEE_ID);

        assertThat(response.getEmployeeId()).isEqualTo(TEST_EMPLOYEE_ID);
    }

    @Test
    void putOffWork() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime offTime = now.plusHours(4);
        Work work = new Work(Employee.builder()
                .id(TEST_EMPLOYEE_ID)
                .build(), now
        );
        work.off(offTime);
        when(workService.goOffWork(eq(TEST_EMPLOYEE_ID), any())).thenReturn(work);

        WorkOffResponse response = workApiController.putOffWork(TEST_EMPLOYEE_ID);

        assertThat(response.getEmployeeId()).isEqualTo(TEST_EMPLOYEE_ID);
        assertThat(response.getEnd()).isEqualTo(offTime);
    }

    @Test
    void getMonthlyWorks() {
        Employee employee = Employee.builder().id(TEST_EMPLOYEE_ID).build();
        Work work1 = new Work(employee, LocalDateTime.of(2024, 3, 1, 10, 0));
        work1.off(LocalDateTime.of(2024, 3, 1, 19, 0));
        Work work2 = new Work(employee, LocalDateTime.of(2024, 3, 2, 10, 0));
        work2.off(LocalDateTime.of(2024, 3, 2, 19, 0));
        Work work3 = new Work(employee, LocalDateTime.of(2024, 3, 3, 10, 0));
        work3.off(LocalDateTime.of(2024, 3, 3, 19, 0));

        when(workService.getMonthlyCompletedWorks(eq(TEST_EMPLOYEE_ID), eq(YearMonth.of(2024, 3)))).thenReturn(List.of(work1, work2, work3));

        MonthlyWorkResponse response = workApiController.getMonthlyWorks(TEST_EMPLOYEE_ID, YearMonth.of(2024, 3));

        assertThat(response.getSum()).isEqualTo(27);
        assertThat(response.getDetail().size()).isEqualTo(3);
    }
}
