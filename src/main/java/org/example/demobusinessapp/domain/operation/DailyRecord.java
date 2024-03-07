package org.example.demobusinessapp.domain.operation;

import lombok.Getter;
import org.example.demobusinessapp.domain.dayOff.DayOff;
import org.example.demobusinessapp.domain.work.Status;
import org.example.demobusinessapp.domain.work.Work;

import java.time.Duration;
import java.time.LocalDate;

@Getter
public class DailyRecord {
    private final LocalDate date;
    private final long workingMinutes;
    private final boolean usingDayOff;

    public DailyRecord(Work work) {
        if(work.getStatus() == Status.ON || work.getEndTime() == null) {
            throw new IllegalArgumentException("아직 종료되지 않은 작업입니다.");
        }
        this.date = work.getStartTime().toLocalDate();
        this.workingMinutes = Duration.between(work.getStartTime(), work.getEndTime()).toMinutes();
        this.usingDayOff = false;
    }

    public DailyRecord (DayOff dayOff) {
        this.date = dayOff.getDate();
        this.workingMinutes = 0;
        this.usingDayOff = true;
    }
}
