package org.example.demobusinessapp.dto.request.team;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TeamCreateRequest {
    @NotBlank
    private String name;
}
