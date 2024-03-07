package org.example.demobusinessapp.controller.work;

import lombok.RequiredArgsConstructor;
import org.example.demobusinessapp.domain.work.Work;
import org.example.demobusinessapp.dto.response.work.WorkOffResponse;
import org.example.demobusinessapp.dto.response.work.WorkOnResponse;
import org.example.demobusinessapp.service.work.WorkService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class WorkApiController {
    private final WorkService workService;

    @PutMapping("/api/v1/work/on")
    public WorkOnResponse putOnWork(@RequestParam("employeeId") Long employeeId) {
        Work work = workService.goOnWork(employeeId, LocalDateTime.now());
        return new WorkOnResponse(work);
    }

    @PutMapping("/api/v1/work/off")
    public WorkOffResponse putOffWork(@RequestParam("employeeId") Long employeeId) {
        Work work = workService.goOffWork(employeeId, LocalDateTime.now());
        return new WorkOffResponse(work);
    }
}
