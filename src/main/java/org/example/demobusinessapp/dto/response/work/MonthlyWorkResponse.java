package org.example.demobusinessapp.dto.response.work;

import lombok.Getter;

import java.util.List;

@Getter
public class MonthlyWorkResponse {
    private final List<DailyRecordResponse> detail;
    private final long sum;

    public MonthlyWorkResponse(List<DailyRecordResponse> dailyRecordResponse, long sum) {
        this.detail = dailyRecordResponse;
        this.sum = sum;
    }
}
