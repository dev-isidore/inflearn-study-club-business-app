package org.example.demobusinessapp.dto.response.work;

import lombok.Getter;

import java.util.List;

@Getter
public class MonthlyWorkResponse {
    private final List<DailyWorkResponse> detail;
    private final long sum;

    public MonthlyWorkResponse(List<DailyWorkResponse> dailyWorkRespons, long sum) {
        this.detail = dailyWorkRespons;
        this.sum = sum;
    }
}
