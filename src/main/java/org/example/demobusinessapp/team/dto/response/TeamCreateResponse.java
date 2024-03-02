package org.example.demobusinessapp.team.dto.response;

import lombok.Getter;
import org.example.demobusinessapp.team.domain.Team;

@Getter
public class TeamCreateResponse {
    private long id;
    private String name;

    public TeamCreateResponse(Team team) {
        this.id = team.getId();
        this.name = team.getName();
    }
}
