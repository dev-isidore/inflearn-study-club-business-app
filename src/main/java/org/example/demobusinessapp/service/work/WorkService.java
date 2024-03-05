package org.example.demobusinessapp.service.work;

import lombok.RequiredArgsConstructor;
import org.example.demobusinessapp.domain.employee.Employee;
import org.example.demobusinessapp.domain.employee.EmployeeRepository;
import org.example.demobusinessapp.domain.work.Status;
import org.example.demobusinessapp.domain.work.Work;
import org.example.demobusinessapp.domain.work.WorkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkService {

    private final WorkRepository workRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public Work goOnWork(Long employeeId, LocalDateTime now) {
        final Employee employee = getEmployeeForWork(employeeId);
        final Work work = employee.goOnWork(now);
        return workRepository.save(work);
    }

    @Transactional
    public Work goOffWork(Long employeeId, LocalDateTime now) {
        final Employee employee = getEmployeeForWork(employeeId);

        Work work = employee.goOffWork(now);

        return workRepository.save(work);
    }

    // todo 실 사용을 한다면 문제되는 경우를 해소시키는 update 동작이 필요하다.

    public List<Work> getMonthlyCompletedWorks(Long employeeId, YearMonth yearMonth) {
        final Employee employee = getEmployeeForWork(employeeId);

        return workRepository.findWorksByEmployeeAndStatusAndStartTimeIsBetween(employee, Status.OFF,
                LocalDateTime.of(yearMonth.atDay(1), LocalTime.MIN),
                LocalDateTime.of(yearMonth.atEndOfMonth(), LocalTime.MAX));
    }

    private Employee getEmployeeForWork(Long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 id입니다."));
    }
}
