package org.example.demobusinessapp.controller.work;

import lombok.RequiredArgsConstructor;
import org.example.demobusinessapp.domain.work.Work;
import org.example.demobusinessapp.dto.response.work.DailyWorkResponse;
import org.example.demobusinessapp.dto.response.work.MonthlyWorkResponse;
import org.example.demobusinessapp.dto.response.work.WorkOffResponse;
import org.example.demobusinessapp.dto.response.work.WorkOnResponse;
import org.example.demobusinessapp.service.work.WorkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class WorkController {
    private final WorkService workService;

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
        List<Work> monthlyCompletedWorks = workService.getMonthlyCompletedWorks(employeeId, yearMonth);
        List<DailyWorkResponse> dailyWorkResponses = new ArrayList<>();
        long sum = 0;
        for (Work work : monthlyCompletedWorks) {
            DailyWorkResponse dailyWorkResponse = DailyWorkResponse.of(work);
            sum += dailyWorkResponse.getWorkingMinutes();
            dailyWorkResponses.add(dailyWorkResponse);
        }
        return new MonthlyWorkResponse(dailyWorkResponses, sum);
    }
}
