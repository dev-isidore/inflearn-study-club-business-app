package org.example.demobusinessapp.controller.operation;

import lombok.RequiredArgsConstructor;
import org.example.demobusinessapp.domain.operation.DailyRecord;
import org.example.demobusinessapp.domain.operation.MonthlyOvertime;
import org.example.demobusinessapp.dto.response.operation.DailyRecordResponse;
import org.example.demobusinessapp.dto.response.operation.MonthlyRecordsResponse;
import org.example.demobusinessapp.dto.response.operation.MonthlyOvertimeResponse;
import org.example.demobusinessapp.service.operation.OperationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OperationApiController {
    private final OperationService operationService;

    @GetMapping("/api/v1/operation/statistics/work")
    public MonthlyRecordsResponse getMonthlyRecords(@RequestParam("employeeId") Long employeeId, @RequestParam("yearMonth") YearMonth yearMonth) {
        List<DailyRecord> dailyRecords = operationService.getMonthlyRecords(employeeId, yearMonth);

        List<DailyRecordResponse> dailyRecordResponses = new ArrayList<>();
        long sum = 0;

        for (DailyRecord dailyRecord : dailyRecords) {
            dailyRecordResponses.add(new DailyRecordResponse(dailyRecord));
            sum += dailyRecord.getWorkingMinutes();
        }

        return new MonthlyRecordsResponse(dailyRecordResponses, sum);
    }

    @GetMapping("/api/v1/operation/statistics/overtime")
    public List<MonthlyOvertimeResponse> getMonthlyOvertime(@RequestParam("yearMonth") YearMonth yearMonth) {
        List<MonthlyOvertime> monthlyOvertimes = operationService.getMonthlyOvertimes(yearMonth);
        List<MonthlyOvertimeResponse> monthlyOvertimeResponses = new ArrayList<>();
        for (MonthlyOvertime monthlyOvertime : monthlyOvertimes) {
            monthlyOvertimeResponses.add(new MonthlyOvertimeResponse(monthlyOvertime));
        }
        return monthlyOvertimeResponses;
    }
}
