package org.example.demobusinessapp.domain.dayOff;

import org.example.demobusinessapp.domain.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DayOffRepository extends JpaRepository<DayOff, Long> {
    List<DayOff> findDayOffsByEmployeeAndDateBetween(Employee employee, LocalDate startDate, LocalDate endDate);
}
