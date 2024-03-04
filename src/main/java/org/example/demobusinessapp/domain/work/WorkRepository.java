package org.example.demobusinessapp.domain.work;

import org.example.demobusinessapp.domain.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkRepository extends JpaRepository<Work, Long> {
    List<Work> findWorksByEmployeeAndStatusAndStartTimeIsBetween(Employee employee, Status status, LocalDateTime beginMin, LocalDateTime beginMax);
}
