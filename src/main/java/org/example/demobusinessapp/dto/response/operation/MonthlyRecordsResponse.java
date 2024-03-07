package org.example.demobusinessapp.dto.response.operation;

import lombok.Getter;

import java.util.List;

@Getter
public class MonthlyRecordsResponse {
    private final List<DailyRecordResponse> detail;
    private final long sum;

    public MonthlyRecordsResponse(List<DailyRecordResponse> dailyRecordResponse, long sum) {
        this.detail = dailyRecordResponse;
        this.sum = sum;
    }
}
