package org.example.demobusinessapp.service.dayOff;

import org.example.demobusinessapp.domain.dayOff.DayOff;
import org.example.demobusinessapp.domain.dayOff.DayOffRepository;
import org.example.demobusinessapp.domain.employee.Employee;
import org.example.demobusinessapp.domain.team.Team;
import org.example.demobusinessapp.service.employee.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DayOffServiceMockTest {
    private static final Long TEST_EMPLOYEE_ID = 1L;
    private static final String TEST_EMPLOYEE_NAME = "John";
    private static final Team TEST_TEAM = new Team("dev");
    private static final Employee TEST_EMPLOYEE = Employee.builder()
            .id(TEST_EMPLOYEE_ID)
            .name(TEST_EMPLOYEE_NAME)
            .team(TEST_TEAM)
            .workStartDate(LocalDate.of(2024, 1, 1))
            .build();

    private static final LocalDate TEST_DATE = LocalDate.of(2024, 3, 6);
    @Mock
    private EmployeeService employeeService;

    @Mock
    private DayOffRepository dayOffRepository;

    @InjectMocks
    private DayOffService dayOffService;

    @BeforeEach
    void setUp() {
        when(employeeService.getEmployee(eq(TEST_EMPLOYEE_ID))).thenReturn(Optional.of(TEST_EMPLOYEE));
        ReflectionTestUtils.setField(TEST_TEAM, "dayOffBuffer", 10);
    }
    @Test
    void goDayOffTest() {
        when(dayOffRepository.findDayOffsByEmployeeAndDateBetween(eq(TEST_EMPLOYEE), any(), any())).thenReturn(Collections.emptyList());
        LocalDate dayOffDate = TEST_DATE.plusDays(2);

        dayOffService.goDayOff(TEST_EMPLOYEE_ID, dayOffDate, TEST_DATE);

        ArgumentCaptor<DayOff> argumentCaptor = ArgumentCaptor.forClass(DayOff.class);
        verify(dayOffRepository).save(argumentCaptor.capture());
        DayOff dayOff = argumentCaptor.getValue();
        assertThat(dayOff.getEmployee()).isEqualTo(TEST_EMPLOYEE);
        assertThat(dayOff.getDate()).isEqualTo(dayOffDate);
    }

    @Test
    void getRemainingYearlyDayOffs() {
        when(dayOffRepository.findDayOffsByEmployeeAndDateBetween(eq(TEST_EMPLOYEE), any(), any())).thenReturn(Collections.emptyList());

        int remainingYearlyDayOffs = dayOffService.getRemainingYearlyDayOffs(TEST_EMPLOYEE_ID, TEST_DATE);

        assertThat(remainingYearlyDayOffs).isEqualTo(11);
    }

    @Test
    void getUsedMonthlyDayOffs() {
        YearMonth yearMonth = YearMonth.of(2024, 3);
        when(dayOffRepository.findDayOffsByEmployeeAndDateBetween(eq(TEST_EMPLOYEE), eq(yearMonth.atDay(1)), eq(yearMonth.atEndOfMonth()))).thenReturn(Collections.emptyList());

        List<DayOff> result = dayOffService.getUsedMonthlyDayOffs(TEST_EMPLOYEE_ID, yearMonth);

        assertThat(result).isEmpty();
    }
}
