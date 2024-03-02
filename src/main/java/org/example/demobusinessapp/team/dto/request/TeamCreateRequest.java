package org.example.demobusinessapp.team.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TeamCreateRequest {
    @NotBlank
    private String name;
}
