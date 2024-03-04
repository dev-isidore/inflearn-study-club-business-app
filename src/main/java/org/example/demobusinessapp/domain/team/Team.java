package org.example.demobusinessapp.domain.team;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.demobusinessapp.domain.employee.Employee;
import org.example.demobusinessapp.domain.employee.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "team")
    private final List<Employee> employees = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }

    public Optional<Employee> getManager() {
        return this.employees.stream().filter(employee -> employee.getRole() == Role.MANAGER).findFirst();
    }
}
