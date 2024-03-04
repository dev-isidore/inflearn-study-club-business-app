package org.example.demobusinessapp.dto.response.work;

import lombok.Getter;
import org.example.demobusinessapp.domain.work.Work;

import java.time.LocalDateTime;

@Getter
public class WorkOnResponse {
    private final LocalDateTime begin;
    private final long employeeId;

    public WorkOnResponse(Work work) {
        this.begin = work.getStartTime();
        this.employeeId = work.getEmployee().getId();
    }
}
