package org.example.demobusinessapp.controller.work;

import lombok.RequiredArgsConstructor;
import org.example.demobusinessapp.domain.dayOff.DayOff;
import org.example.demobusinessapp.domain.work.Work;
import org.example.demobusinessapp.dto.response.work.DailyRecordResponse;
import org.example.demobusinessapp.dto.response.work.MonthlyWorkResponse;
import org.example.demobusinessapp.dto.response.work.WorkOffResponse;
import org.example.demobusinessapp.dto.response.work.WorkOnResponse;
import org.example.demobusinessapp.service.dayOff.DayOffService;
import org.example.demobusinessapp.service.work.WorkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;

@RestController
@RequiredArgsConstructor
public class WorkApiController {
    private final WorkService workService;
    private final DayOffService dayOffService;

    @PutMapping("/api/v1/work/on")
    public WorkOnResponse putOnWork(@RequestParam("employeeId") Long employeeId) {
        Work work = workService.goOnWork(employeeId, LocalDateTime.now());
        return new WorkOnResponse(work);
    }

    @PutMapping("/api/v1/work/off")
    public WorkOffResponse putOffWork(@RequestParam("employeeId") Long employeeId) {
        Work work = workService.goOffWork(employeeId, LocalDateTime.now());
        return new WorkOffResponse(work);
    }

    @GetMapping("/api/v1/work")
    public MonthlyWorkResponse getMonthlyWorks(@RequestParam("employeeId") Long employeeId, @RequestParam("yearMonth") YearMonth yearMonth) {
        // 월간 결산 동작이 타 서비스에서도 사용된다면 별도 서비스로 분리 고려 필요
        List<Work> monthlyCompletedWorks = workService.getMonthlyCompletedWorks(employeeId, yearMonth);
        List<DayOff> usedMonthlyDayOffs = dayOffService.getUsedMonthlyDayOffs(employeeId, yearMonth);
        List<DailyRecordResponse> dailyRecordResponses = new ArrayList<>();
        long sum = 0;
        for (Work work : monthlyCompletedWorks) {
            DailyRecordResponse dailyRecordResponse = DailyRecordResponse.of(work);
            sum += dailyRecordResponse.getWorkingMinutes();
            dailyRecordResponses.add(dailyRecordResponse);
        }
        for (DayOff dayOff : usedMonthlyDayOffs) {
            DailyRecordResponse dailyRecordResponse = DailyRecordResponse.of(dayOff);
            dailyRecordResponses.add(dailyRecordResponse);
        }
        dailyRecordResponses.sort(comparing(DailyRecordResponse::getDate));
        return new MonthlyWorkResponse(dailyRecordResponses, sum);
    }
}
