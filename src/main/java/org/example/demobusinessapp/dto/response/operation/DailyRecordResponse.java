package org.example.demobusinessapp.dto.response.operation;

import lombok.Getter;
import org.example.demobusinessapp.domain.operation.DailyRecord;

import java.time.LocalDate;

@Getter
public class DailyRecordResponse {
    private LocalDate date;
    private long workingMinutes;
    private boolean usingDayOff;

    public DailyRecordResponse(DailyRecord dailyRecord) {
        this.date = dailyRecord.getDate();
        this.workingMinutes = dailyRecord.getWorkingMinutes();
        this.usingDayOff = dailyRecord.isUsingDayOff();
    }
}
