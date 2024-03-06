package org.example.demobusinessapp.dto.response.dayOff;

import lombok.Getter;

@Getter
public class RemainingYearlyDayOffsResponse {
    private final int remainingYearlyDayOffs;

    public RemainingYearlyDayOffsResponse(int remainingYearlyDayOffs) {
        this.remainingYearlyDayOffs = remainingYearlyDayOffs;
    }
}
