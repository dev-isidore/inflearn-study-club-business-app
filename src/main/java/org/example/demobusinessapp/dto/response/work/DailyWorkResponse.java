package org.example.demobusinessapp.dto.response.work;

import lombok.Getter;
import org.example.demobusinessapp.domain.work.Status;
import org.example.demobusinessapp.domain.work.Work;

import java.time.Duration;
import java.time.LocalDate;

@Getter
public class DailyWorkResponse {
    private LocalDate date;
    private long workingMinutes;

    public static DailyWorkResponse of(Work work) {
        if(work.getStatus() == Status.ON || work.getEndTime() == null) {
            throw new IllegalArgumentException("아직 종료되지 않은 작업입니다.");
        }
        DailyWorkResponse dailyWorkResponse = new DailyWorkResponse();
        dailyWorkResponse.date = work.getStartTime().toLocalDate();
        dailyWorkResponse.workingMinutes = Duration.between(work.getStartTime(), work.getEndTime()).toMinutes();
        return dailyWorkResponse;
    }
}
