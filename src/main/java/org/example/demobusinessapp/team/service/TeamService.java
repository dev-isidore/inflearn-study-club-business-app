package org.example.demobusinessapp.team.service;

import lombok.RequiredArgsConstructor;
import org.example.demobusinessapp.team.domain.Team;
import org.example.demobusinessapp.team.domain.TeamRepository;
import org.example.demobusinessapp.team.dto.request.TeamCreateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional
    public Team createTeam(TeamCreateRequest request) {
        Team team = new Team(request.getName());
        return teamRepository.save(team);
    }

    public List<Team> getTeams() {
        return teamRepository.findAll();
    }
}
