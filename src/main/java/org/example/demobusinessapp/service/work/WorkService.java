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
    public Work goOnWork(Long workerId, LocalDateTime now) {
        Employee employee = employeeRepository.findById(workerId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 id입니다."));

        Work work = new Work(employee, now);

        List<Work> workList = employee.getWorkList();
        if(workList.isEmpty()) {
            workList.add(work);
            return workRepository.save(work);
        }

        Work lastWork = workList.getLast();
        if(lastWork.getStatus() == Status.ON) {
            if(lastWork.getStartTime().isBefore(LocalDateTime.of(now.toLocalDate(), LocalTime.MIN))) {
                throw new IllegalArgumentException("완료되지 않은 이전 근무 기록이 있습니다.");
            }
            throw new IllegalArgumentException("금일 출근한 기록이 있습니다.");
        }

        workList.add(work);
        return workRepository.save(work);
    }

    @Transactional
    public Work goOffWork(Long workerId, LocalDateTime now) {
        Employee employee = employeeRepository.findById(workerId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 id입니다."));

        Work lastWork = employee.getWorkList().getLast();

        if (lastWork.getStartTime().isBefore(LocalDateTime.of(now.toLocalDate(), LocalTime.MIN))) {
            throw new IllegalArgumentException("금일 출근한 이력이 없습니다.");
        }
        lastWork.off(now);

        return lastWork;
    }

    // todo 현재 기능상 문제되는 경우를 해소시키는 update하는 동작이 필요하다.

    public List<Work> getMonthlyCompletedWorks(Long workerId, YearMonth yearMonth) {
        Employee employee = employeeRepository.findById(workerId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 id입니다."));

        return workRepository.findWorksByEmployeeAndStatusAndStartTimeIsBetween(employee, Status.OFF,
                LocalDateTime.of(yearMonth.atDay(1), LocalTime.MIDNIGHT),
                LocalDateTime.of(yearMonth.atEndOfMonth().plusDays(1), LocalTime.MIN));
    }
}
