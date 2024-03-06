package org.example.demobusinessapp.domain.dayOff;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.demobusinessapp.domain.employee.Employee;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DayOff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private LocalDate date;

    public DayOff(Employee employee, LocalDate date) {
        this.employee = employee;
        this.date = date;
    }
}
