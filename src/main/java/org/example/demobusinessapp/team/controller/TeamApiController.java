package org.example.demobusinessapp.team.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.demobusinessapp.team.domain.Team;
import org.example.demobusinessapp.team.dto.request.TeamCreateRequest;
import org.example.demobusinessapp.team.dto.response.TeamCreateResponse;
import org.example.demobusinessapp.team.service.TeamService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TeamApiController {

    private final TeamService teamService;

    @PostMapping("/api/v1/team")
    public TeamCreateResponse createTeam(@RequestBody @Valid TeamCreateRequest request) {
        Team createdTeam = teamService.createTeam(request);
        return new TeamCreateResponse(createdTeam);
    }
}
