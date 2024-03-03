package org.example.demobusinessapp.team.service;

import org.example.demobusinessapp.team.domain.Team;
import org.example.demobusinessapp.team.domain.TeamRepository;
import org.example.demobusinessapp.team.dto.request.TeamCreateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamServiceMockTest {
    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamService teamService;

    @Test
    void createTeam() {
        final String teamName = "dev";
        TeamCreateRequest request = mock(TeamCreateRequest.class);
        when(request.getName()).thenReturn(teamName);

        ArgumentCaptor<Team> argumentCaptor = ArgumentCaptor.forClass(Team.class);

        Team teamResult = mock(Team.class);
        when(teamResult.getId()).thenReturn(1L);

        when(teamRepository.save(any())).thenReturn(teamResult);

        Team actual = teamService.createTeam(request);

        verify(teamRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getName()).isEqualTo(teamName);
        assertThat(actual.getId()).isEqualTo(1L);
    }

    @Test
    void getTeams() {
        Team team1 = new Team("dev");
        Team team2 = new Team("QA");

        when(teamRepository.findAll()).thenReturn(List.of(team1, team2));

        List<Team> result = teamService.getTeams();

        assertThat(result.size()).isEqualTo(2);
    }
}