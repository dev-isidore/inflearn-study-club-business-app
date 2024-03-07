package org.example.demobusinessapp.controller.operation;

import org.example.demobusinessapp.domain.dayOff.DayOff;
import org.example.demobusinessapp.domain.employee.Employee;
import org.example.demobusinessapp.domain.work.Work;
import org.example.demobusinessapp.dto.response.operation.MonthlyRecordsResponse;
import org.example.demobusinessapp.service.operation.OperationService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class OperationApiControllerMockTest {
    private static final Long TEST_EMPLOYEE_ID = 1L;

    @Mock
    private OperationService operationService;

    @InjectMocks
    private OperationApiController operationApiController;

    @Test
    void getMonthlyWorks() {
        Employee employee = Employee.builder().id(TEST_EMPLOYEE_ID).build();
        Work work1 = new Work(employee, LocalDateTime.of(2024, 3, 1, 10, 0));
        work1.off(LocalDateTime.of(2024, 3, 1, 19, 0));
        Work work2 = new Work(employee, LocalDateTime.of(2024, 3, 3, 10, 0));
        work2.off(LocalDateTime.of(2024, 3, 3, 19, 0));
        Work work3 = new Work(employee, LocalDateTime.of(2024, 3, 4, 10, 0));
        work3.off(LocalDateTime.of(2024, 3, 4, 19, 0));

        when(workService.getMonthlyCompletedWorks(eq(TEST_EMPLOYEE_ID), eq(YearMonth.of(2024, 3)))).thenReturn(List.of(work1, work2, work3));

        DayOff dayOff = new DayOff(employee, LocalDate.of(2024, 3, 2));
        when(dayOffService.getUsedMonthlyDayOffs(eq(TEST_EMPLOYEE_ID), eq(YearMonth.of(2024, 3)))).thenReturn(List.of(dayOff));

        MonthlyRecordsResponse response = workApiController.getMonthlyWorks(TEST_EMPLOYEE_ID, YearMonth.of(2024, 3));

        assertThat(response.getSum()).isEqualTo(3 * 9 * 60);
        assertThat(response.getDetail().size()).isEqualTo(4);
        assertThat(response.getDetail().get(1).isUsingDayOff()).isTrue();
    }
}
