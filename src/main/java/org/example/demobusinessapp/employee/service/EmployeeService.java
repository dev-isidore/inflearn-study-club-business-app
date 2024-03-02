package org.example.demobusinessapp.employee.service;

import lombok.RequiredArgsConstructor;
import org.example.demobusinessapp.employee.domain.Employee;
import org.example.demobusinessapp.employee.domain.EmployeeRepository;
import org.example.demobusinessapp.employee.domain.Role;
import org.example.demobusinessapp.employee.dto.request.EmployeeCreateRequest;
import org.example.demobusinessapp.team.domain.Team;
import org.example.demobusinessapp.team.domain.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public Employee createEmployee(EmployeeCreateRequest request) {
        Employee.EmployeeBuilder builder = Employee.builder();
        builder.name(request.getName())
                .birthday(request.getBirthday())
                .workStartDate(request.getWorkStartDate());

        if(request.getTeamName() == null) {
            return employeeRepository.save(builder.build());
        }

        Team team = teamRepository.findByName(request.getTeamName()).orElseThrow(()-> new IllegalArgumentException("설정할 수 없는 팀입니다."));

        if (request.getRole() == Role.MANAGER && team.getEmployees().stream().anyMatch(employee -> employee.getRole() == Role.MANAGER)) {
            throw new IllegalArgumentException("이미 매니저가 있는 팀입니다.");
        }

        builder.team(team)
                .role(request.getRole());
        return employeeRepository.save(builder.build());
    }
}
