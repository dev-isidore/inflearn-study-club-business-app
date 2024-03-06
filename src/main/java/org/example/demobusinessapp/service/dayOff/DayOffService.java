package org.example.demobusinessapp.service.dayOff;

import lombok.RequiredArgsConstructor;
import org.example.demobusinessapp.domain.dayOff.DayOff;
import org.example.demobusinessapp.domain.dayOff.DayOffRepository;
import org.example.demobusinessapp.domain.employee.Employee;
import org.example.demobusinessapp.service.employee.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DayOffService {
    private final EmployeeService employeeService;
    private final DayOffRepository dayOffRepository;

    @Transactional
    public DayOff goDayOff(Long employeeId, LocalDate dayOffDate, LocalDate now) {
        final Employee employee = getEmployeeForDayOff(employeeId);
        final List<DayOff> yearlyUsedDayOffs = getUsedYearlyDayOffs(employee, dayOffDate);
        final DayOff dayOff = employee.goDayOff(yearlyUsedDayOffs, dayOffDate, now);
        return dayOffRepository.save(dayOff);
    }

    public int getRemainingYearlyDayOffs(Long employeeId, LocalDate now) {
        final Employee employee = getEmployeeForDayOff(employeeId);
        final List<DayOff> yearlyUsedDayOffs = getUsedYearlyDayOffs(employee, now);
        return employee.getRemainingYearlyDayOffs(yearlyUsedDayOffs, now);
    }

    public List<DayOff> getUsedMonthlyDayOffs(Long employeeId, YearMonth yearMonth) {
        final Employee employee = getEmployeeForDayOff(employeeId);

        return dayOffRepository.findDayOffsByEmployeeAndDateBetween(employee,
                yearMonth.atDay(1), yearMonth.atEndOfMonth());
    }

    private List<DayOff> getUsedYearlyDayOffs(Employee employee, LocalDate date) {
        return dayOffRepository.findDayOffsByEmployeeAndDateBetween(employee,
                LocalDate.of(date.getYear(), 1, 1),
                LocalDate.of(date.getYear(), 12, 31));
    }

    private Employee getEmployeeForDayOff(Long employeeId) {
        return employeeService.getEmployee(employeeId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 id입니다."));
    }
}
