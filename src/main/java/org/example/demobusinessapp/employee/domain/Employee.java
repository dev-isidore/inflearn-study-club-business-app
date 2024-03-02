package org.example.demobusinessapp.employee.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.demobusinessapp.team.domain.Team;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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

    @Builder
    public Employee(Long id, String name, Team team, Role role, LocalDate birthday, LocalDate workStartDate) {
        this.id = id;
        this.name = name;
        this.team = team;
        this.role = role;
        this.birthday = birthday;
        this.workStartDate = workStartDate;
    }
}
