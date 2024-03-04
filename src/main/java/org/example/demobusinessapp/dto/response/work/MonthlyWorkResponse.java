package org.example.demobusinessapp.dto.response.work;

import lombok.Getter;

import java.util.List;

@Getter
public class MonthlyWorkResponse {
    private final List<DailyWorkResponse> detail;
    private final long sum;

    public MonthlyWorkResponse(List<DailyWorkResponse> dailyWorkResponse, long sum) {
        this.detail = dailyWorkResponse;
        this.sum = sum;
    }
}
