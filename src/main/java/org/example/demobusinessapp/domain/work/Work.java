package org.example.demobusinessapp.domain.work;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.demobusinessapp.domain.employee.Employee;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Employee employee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    public Work(Employee employee, LocalDateTime startTime) {
        this.employee = employee;
        this.startTime = startTime;
        this.status = Status.ON;
    }

    public void off(LocalDateTime offTime) {
        if(this.status == Status.OFF) {
            throw new IllegalArgumentException("현재 출근한 상태가 아닙니다.");
        }
        this.endTime = offTime;
    }
}
