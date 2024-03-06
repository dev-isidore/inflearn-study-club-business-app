package org.example.demobusinessapp.dto.response.work;

import lombok.Getter;
import org.example.demobusinessapp.domain.dayOff.DayOff;
import org.example.demobusinessapp.domain.work.Status;
import org.example.demobusinessapp.domain.work.Work;

import java.time.Duration;
import java.time.LocalDate;

@Getter
public class DailyRecordResponse {
    private LocalDate date;
    private long workingMinutes;
    private boolean usingDayOff;

    public static DailyRecordResponse of(Work work) {
        if(work.getStatus() == Status.ON || work.getEndTime() == null) {
            throw new IllegalArgumentException("아직 종료되지 않은 작업입니다.");
        }
        DailyRecordResponse dailyRecordResponse = new DailyRecordResponse();
        dailyRecordResponse.date = work.getStartTime().toLocalDate();
        dailyRecordResponse.workingMinutes = Duration.between(work.getStartTime(), work.getEndTime()).toMinutes();
        dailyRecordResponse.usingDayOff = false;
        return dailyRecordResponse;
    }

    public static DailyRecordResponse of(DayOff dayOff) {
        DailyRecordResponse dailyRecordResponse = new DailyRecordResponse();
        dailyRecordResponse.date = dayOff.getDate();
        dailyRecordResponse.workingMinutes = 0;
        dailyRecordResponse.usingDayOff = true;
        return dailyRecordResponse;
    }
}
