package org.example.demobusinessapp.controller.dayOff;

import lombok.RequiredArgsConstructor;
import org.example.demobusinessapp.domain.dayOff.DayOff;
import org.example.demobusinessapp.dto.response.dayOff.NewDayOffResponse;
import org.example.demobusinessapp.dto.response.dayOff.RemainingYearlyDayOffsResponse;
import org.example.demobusinessapp.service.dayOff.DayOffService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class DayOffApiController {
    private final DayOffService dayOffService;

    @PutMapping("/api/v1/dayOff")
    public NewDayOffResponse putDayOff(@RequestParam("employeeId") Long employeeId, @RequestParam("date") String date) {
        final DayOff dayOff = dayOffService.goDayOff(employeeId, LocalDate.parse(date), LocalDate.now());
        return new NewDayOffResponse(dayOff);
    }

    @GetMapping("/api/v1/dayOff")
    public RemainingYearlyDayOffsResponse getRemainingDayOff(@RequestParam("employeeId") Long employeeId) {
        final int remainingYearlyDayOffs = dayOffService.getRemainingYearlyDayOffs(employeeId, LocalDate.now());
        return new RemainingYearlyDayOffsResponse(remainingYearlyDayOffs);
    }
}
