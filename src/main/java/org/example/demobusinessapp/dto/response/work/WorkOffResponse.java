package org.example.demobusinessapp.dto.response.work;

import lombok.Getter;
import org.example.demobusinessapp.domain.work.Work;

import java.time.LocalDateTime;

@Getter
public class WorkOffResponse {

    private final LocalDateTime begin;
    private final LocalDateTime end;
    private final long employeeId;

    public WorkOffResponse(Work work) {
        this.begin = work.getStartTime();
        this.end = work.getEndTime();
        this.employeeId = work.getEmployee().getId();
    }
}
