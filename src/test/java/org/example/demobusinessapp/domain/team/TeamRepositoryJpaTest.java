package org.example.demobusinessapp.domain.team;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class TeamRepositoryJpaTest {
    @Autowired
    private TeamRepository teamRepository;
    private Team devTeam;
    @BeforeEach
    void setUp() {
        devTeam = teamRepository.save(new Team("DEV"));
    }

    @Test
    void findByName() {
        {
            assertThat(teamRepository.findByName("DEV").get()).isEqualTo(devTeam);
        }
        {
            assertThat(teamRepository.findByName("QA").isPresent()).isFalse();
        }
    }
}
