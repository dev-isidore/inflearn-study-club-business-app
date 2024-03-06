package org.example.demobusinessapp.dto.response.dayOff;

import lombok.Getter;
import org.example.demobusinessapp.domain.dayOff.DayOff;

import java.time.LocalDate;

@Getter
public class NewDayOffResponse {
    private final LocalDate date;
    private final long employeeId;
    private final String employeeName;

    public NewDayOffResponse(DayOff dayOff) {
        this.date = dayOff.getDate();
        this.employeeId = dayOff.getEmployee().getId();
        this.employeeName = dayOff.getEmployee().getName();
    }
}
