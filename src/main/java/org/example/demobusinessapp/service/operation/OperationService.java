package org.example.demobusinessapp.service.operation;

import lombok.RequiredArgsConstructor;
import org.example.demobusinessapp.domain.employee.Employee;
import org.example.demobusinessapp.domain.operation.DailyRecord;
import org.example.demobusinessapp.domain.dayOff.DayOff;
import org.example.demobusinessapp.domain.operation.MonthlyOvertime;
import org.example.demobusinessapp.domain.work.Work;
import org.example.demobusinessapp.service.dayOff.DayOffService;
import org.example.demobusinessapp.service.employee.EmployeeService;
import org.example.demobusinessapp.service.work.WorkService;
import org.example.demobusinessapp.service.workday.WorkDayService;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;

@Service
@RequiredArgsConstructor
public class OperationService {
    private final WorkService workService;
    private final DayOffService dayOffService;
    private final EmployeeService employeeService;
    private final WorkDayService workDayService;

    public List<DailyRecord> getMonthlyRecords(Long employeeId, YearMonth yearMonth) {
        List<Work> monthlyCompletedWorks = workService.getMonthlyCompletedWorks(employeeId, yearMonth);
        List<DayOff> usedMonthlyDayOffs = dayOffService.getUsedMonthlyDayOffs(employeeId, yearMonth);

        List<DailyRecord> dailyRecords = new ArrayList<>();
        for (Work work : monthlyCompletedWorks) {
            DailyRecord dailyRecord = new DailyRecord(work);
            dailyRecords.add(dailyRecord);
        }
        for (DayOff dayOff : usedMonthlyDayOffs) {
            DailyRecord dailyRecord = new DailyRecord(dayOff);
            dailyRecords.add(dailyRecord);
        }
        dailyRecords.sort(comparing(DailyRecord::getDate));

        return dailyRecords;
    }


    public List<MonthlyOvertime> getMonthlyOvertimes(YearMonth yearMonth) {
        List<Employee> employees = employeeService.getEmployees();
        final int monthlyBusinessDays = workDayService.getBusinessDaysOfYearMonth(yearMonth);

        List<MonthlyOvertime> monthlyOvertimes = new ArrayList<>();
        for (Employee employee : employees) {
            monthlyOvertimes.add(new MonthlyOvertime(employee, monthlyBusinessDays));
        }
        return monthlyOvertimes;
    }
}
