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

        final Work work = new Work(employee, now);

        final List<Work> workList = employee.getWorkList();
        if(workList.isEmpty()) {
            workList.add(work);
            return workRepository.save(work);
        }

        final Work lastWork = workList.getLast();
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
    public Work goOffWork(Long employeeId, LocalDateTime now) {
        final Employee employee = getEmployeeForWork(employeeId);

        final Work lastWork = employee.getWorkList().getLast();

        if (lastWork.getStartTime().isBefore(LocalDateTime.of(now.toLocalDate(), LocalTime.MIN))) {
            throw new IllegalArgumentException("금일 출근한 이력이 없습니다.");
        }
        lastWork.off(now);

        return workRepository.save(lastWork);
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
