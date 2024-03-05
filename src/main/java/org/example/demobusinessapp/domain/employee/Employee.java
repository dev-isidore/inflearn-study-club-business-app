package org.example.demobusinessapp.domain.employee;

import jakarta.persistence.*;
import lombok.*;
import org.example.demobusinessapp.domain.team.Team;
import org.example.demobusinessapp.domain.work.Status;
import org.example.demobusinessapp.domain.work.Work;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    public Work goOnWork(LocalDateTime now) {
        if (this.workList.isEmpty()) {
            final Work work = new Work(this, now);
            this.workList.add(work);
            return work;
        }

        final Work lastWork = this.workList.getLast();
        if(lastWork.getStatus() == Status.ON) {
            if(lastWork.getStartTime().isBefore(LocalDateTime.of(now.toLocalDate(), LocalTime.MIN))) {
                throw new IllegalArgumentException("완료되지 않은 이전 근무 기록이 있습니다.");
            }
            throw new IllegalArgumentException("금일 출근한 기록이 있습니다.");
        }

        final Work work = new Work(this, now);
        this.workList.add(work);

        return work;
    }

    public Work goOffWork(LocalDateTime now) {
        final Work lastWork = this.workList.getLast();

        if (lastWork.getStartTime().isBefore(LocalDateTime.of(now.toLocalDate(), LocalTime.MIN))) {
            throw new IllegalArgumentException("금일 출근한 이력이 없습니다.");
        }
        lastWork.off(now);

        return lastWork;
    }
}
