package org.example.demobusinessapp.employee.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.demobusinessapp.team.domain.Team;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Team team;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDate birthday;

    private LocalDate workStartDate;
}
