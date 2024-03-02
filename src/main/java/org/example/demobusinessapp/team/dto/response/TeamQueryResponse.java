package org.example.demobusinessapp.team.dto.response;

import lombok.Getter;
import org.example.demobusinessapp.team.domain.Team;

@Getter
public class TeamQueryResponse {
    private String name;
    private String manager;
    private int memberCount;

    public TeamQueryResponse(Team team) {
        this.name = team.getName();
        team.getManager().ifPresent(manager -> this.manager = manager.getName());
        this.memberCount = team.getEmployees().size();
    }
}
