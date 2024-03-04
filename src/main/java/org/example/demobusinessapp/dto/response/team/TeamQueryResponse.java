package org.example.demobusinessapp.dto.response.team;

import lombok.Getter;
import org.example.demobusinessapp.domain.team.Team;

@Getter
public class TeamQueryResponse {
    private final String name;
    private String manager;
    private final int memberCount;

    public TeamQueryResponse(Team team) {
        this.name = team.getName();
        team.getManager().ifPresent(manager -> this.manager = manager.getName());
        this.memberCount = team.getEmployees().size();
    }
}
