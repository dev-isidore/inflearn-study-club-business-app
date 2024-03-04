package org.example.demobusinessapp.service.work;

import org.example.demobusinessapp.domain.employee.Employee;
import org.example.demobusinessapp.domain.employee.EmployeeRepository;
import org.example.demobusinessapp.domain.work.Work;
import org.example.demobusinessapp.domain.work.WorkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WorkServiceMockTest {
    private static final Long TEST_EMPLOYEE_ID = 1L;
    private static final Employee TEST_EMPLOYEE = Employee.builder().name("John").id(TEST_EMPLOYEE_ID).build();
    private static final LocalDateTime TEST_ON_TIME1 = LocalDateTime.of(2024, 3, 4, 10, 0);
    private static final LocalDateTime TEST_OFF_TIME1 = LocalDateTime.of(2024, 3, 4, 19, 0);
    private static final LocalDateTime TEST_ON_TIME2 = LocalDateTime.of(2024, 3, 5, 10, 0);
    private static final LocalDateTime TEST_OFF_TIME2 = LocalDateTime.of(2024, 3, 5, 19, 0);

    @Mock
    private WorkRepository workRepository;
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private WorkService workService;

    @BeforeEach
    void setUp() {
        when(employeeRepository.findById(eq(TEST_EMPLOYEE_ID))).thenReturn(Optional.of(TEST_EMPLOYEE));
    }

    @Test
    void goOnWork_first() {
        Work work = new Work(TEST_EMPLOYEE, TEST_ON_TIME1);

        workService.goOnWork(TEST_EMPLOYEE_ID, TEST_ON_TIME1);

        ArgumentCaptor<Work> argumentCaptor = ArgumentCaptor.forClass(Work.class);
        verify(workRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(work);

        assertThat(TEST_EMPLOYEE.getWorkList().size()).isEqualTo(1);
    }

    @Test
    void goOnWork_second() {
        Work work1 = new Work(TEST_EMPLOYEE, TEST_ON_TIME1);
        work1.off(TEST_OFF_TIME1);
        TEST_EMPLOYEE.getWorkList().add(work1);

        Work work2 = new Work(TEST_EMPLOYEE, TEST_ON_TIME2);

        workService.goOnWork(TEST_EMPLOYEE_ID, TEST_ON_TIME2);

        ArgumentCaptor<Work> argumentCaptor = ArgumentCaptor.forClass(Work.class);
        verify(workRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(work2);

        assertThat(TEST_EMPLOYEE.getWorkList().size()).isEqualTo(2);
    }

    @Test
    void goOnWork_not_yet_off_previously() {
        Work work1 = new Work(TEST_EMPLOYEE, TEST_ON_TIME1);
        TEST_EMPLOYEE.getWorkList().add(work1);

        assertThrows(IllegalArgumentException.class, () -> workService.goOnWork(TEST_EMPLOYEE_ID, TEST_ON_TIME2));
    }

    @Test
    void goOnWork_not_yet_off_today() {
        Work work1 = new Work(TEST_EMPLOYEE, TEST_ON_TIME1);
        TEST_EMPLOYEE.getWorkList().add(work1);

        Work work2 = new Work(TEST_EMPLOYEE, TEST_ON_TIME2);
        TEST_EMPLOYEE.getWorkList().add(work2);

        assertThrows(IllegalArgumentException.class, () -> workService.goOnWork(TEST_EMPLOYEE_ID, TEST_OFF_TIME2));
    }
}
