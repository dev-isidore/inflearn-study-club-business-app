package org.example.demobusinessapp.controller.team;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.demobusinessapp.domain.team.Team;
import org.example.demobusinessapp.dto.request.team.TeamCreateRequest;
import org.example.demobusinessapp.dto.response.team.TeamCreateResponse;
import org.example.demobusinessapp.dto.response.team.TeamQueryResponse;
import org.example.demobusinessapp.service.team.TeamService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeamApiController {

    private final TeamService teamService;

    @PostMapping("/api/v1/team")
    public TeamCreateResponse createTeam(@RequestBody @Valid TeamCreateRequest request) {
        Team createdTeam = teamService.createTeam(request);
        return new TeamCreateResponse(createdTeam);
    }

    @GetMapping("/api/v1/teams")
    public List<TeamQueryResponse> getTeams() {
        List<TeamQueryResponse> responses = new ArrayList<>();
        List<Team> teams = teamService.getTeams();
        if(teams == null) {
            return responses;
        }
        for (Team team : teams) {
            responses.add(new TeamQueryResponse(team));
        }
        return responses;
    }
}
