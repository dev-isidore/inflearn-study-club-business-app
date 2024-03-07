package org.example.demobusinessapp.controller.team;

import org.example.demobusinessapp.controller.team.TeamApiController;
import org.example.demobusinessapp.domain.team.Team;
import org.example.demobusinessapp.dto.request.team.TeamCreateRequest;
import org.example.demobusinessapp.dto.response.team.TeamCreateResponse;
import org.example.demobusinessapp.dto.response.team.TeamQueryResponse;
import org.example.demobusinessapp.service.team.TeamService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamApiControllerMockTest {
    @Mock
    TeamService teamService;

    @InjectMocks
    TeamApiController teamApiController;

    @Test
    void createTeam() {
        final String teamName = "dev";
        Team team = mock(Team.class);
        when(team.getId()).thenReturn(1L);
        when(team.getName()).thenReturn(teamName);
        when(teamService.createTeam(any())).thenReturn(team);

        TeamCreateResponse response = teamApiController.createTeam(new TeamCreateRequest());

        assertThat(response.getName()).isEqualTo(teamName);
    }

    @Test
    void getTeams() {
        Team team1 = new Team("dev");
        Team team2 = new Team("QA");
        when(teamService.getTeams()).thenReturn(List.of(team1, team2));

        List<TeamQueryResponse> responseList = teamApiController.getTeams();

        assertThat(responseList.size()).isEqualTo(2);
    }

    @Test
    void getTeams_empty() {
        {
            List<TeamQueryResponse> responseList = teamApiController.getTeams();

            assertThat(responseList.size()).isEqualTo(0);
        }
        {
            when(teamService.getTeams()).thenReturn(emptyList());

            List<TeamQueryResponse> responseList = teamApiController.getTeams();

            assertThat(responseList.size()).isEqualTo(0);
        }
    }
}