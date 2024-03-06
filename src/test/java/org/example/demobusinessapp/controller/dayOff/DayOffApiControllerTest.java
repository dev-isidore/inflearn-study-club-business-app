package org.example.demobusinessapp.controller.dayOff;

import org.example.demobusinessapp.domain.dayOff.DayOff;
import org.example.demobusinessapp.domain.employee.Employee;
import org.example.demobusinessapp.dto.response.dayOff.NewDayOffResponse;
import org.example.demobusinessapp.dto.response.dayOff.RemainingYearlyDayOffsResponse;
import org.example.demobusinessapp.service.dayOff.DayOffService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DayOffApiControllerTest {
    private static final Long TEST_EMPLOYEE_ID = 1L;
    private static final String TEST_EMPLOYEE_NAME = "John";
    private static final Employee TEST_EMPLOYEE = Employee.builder().id(TEST_EMPLOYEE_ID).name(TEST_EMPLOYEE_NAME).build();
    private static final LocalDate TEST_DATE = LocalDate.of(2024, 3, 6);
    @Mock
    private DayOffService dayOffService;

    @InjectMocks
    private DayOffApiController dayOffApiController;

    @Test
    void putDayOff() {
        DayOff dayOff = new DayOff(TEST_EMPLOYEE, TEST_DATE);
        when(dayOffService.goDayOff(eq(TEST_EMPLOYEE_ID), eq(TEST_DATE), any())).thenReturn(dayOff);

        NewDayOffResponse response = dayOffApiController.putDayOff(TEST_EMPLOYEE_ID, "2024-03-06");

        assertThat(response.getDate()).isEqualTo(TEST_DATE);
        assertThat(response.getEmployeeId()).isEqualTo(TEST_EMPLOYEE_ID);
        assertThat(response.getEmployeeName()).isEqualTo(TEST_EMPLOYEE_NAME);
    }

    @Test
    void getRemainingDayOff() {
        when(dayOffService.getRemainingYearlyDayOffs(eq(TEST_EMPLOYEE_ID), any())).thenReturn(1);

        RemainingYearlyDayOffsResponse response = dayOffApiController.getRemainingDayOff(TEST_EMPLOYEE_ID);

        assertThat(response.getRemainingYearlyDayOffs()).isEqualTo(1);
    }
}