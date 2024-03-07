package org.example.demobusinessapp.controller.work;

import org.example.demobusinessapp.domain.dayOff.DayOff;
import org.example.demobusinessapp.domain.employee.Employee;
import org.example.demobusinessapp.domain.work.Work;
import org.example.demobusinessapp.dto.response.operation.MonthlyRecordsResponse;
import org.example.demobusinessapp.dto.response.work.WorkOffResponse;
import org.example.demobusinessapp.dto.response.work.WorkOnResponse;
import org.example.demobusinessapp.service.dayOff.DayOffService;
import org.example.demobusinessapp.service.work.WorkService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
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
}
