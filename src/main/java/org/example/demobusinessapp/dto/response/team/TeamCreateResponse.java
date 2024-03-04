package org.example.demobusinessapp.dto.response.team;

import lombok.Getter;
import org.example.demobusinessapp.domain.team.Team;

@Getter
public class TeamCreateResponse {
    private long id;
    private String name;

    public TeamCreateResponse(Team team) {
        this.id = team.getId();
        this.name = team.getName();
    }
}
