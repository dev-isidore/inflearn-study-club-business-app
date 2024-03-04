package org.example.demobusinessapp.domain.employee;

import jakarta.persistence.*;
import lombok.*;
import org.example.demobusinessapp.domain.team.Team;
import org.example.demobusinessapp.domain.work.Work;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    @JoinColumn(nullable = false)
    private Team team;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(nullable = false)
    private LocalDate workStartDate;

    @OneToMany(mappedBy = "employee")
    private final List<Work> workList = new ArrayList<>();

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
