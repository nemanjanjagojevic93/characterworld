package com.njaga.characterworld.service;

import com.njaga.characterworld.dto.RaceResponseDTO;
import com.njaga.characterworld.entity.Race;
import com.njaga.characterworld.mapper.RaceRowMapper;
import com.njaga.characterworld.repository.RaceRepository;
import com.njaga.characterworld.service.impl.RaceServiceImpl;
import com.njaga.characterworld.util.CharacterWorldTestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class RaceServiceImplTest {

    @Mock
    private RaceRepository raceRepository;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private RaceRowMapper raceRowMapper;

    @InjectMocks
    private RaceServiceImpl raceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void getOne() {
    }

    @Test
    void testGetOne() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getAllList() {
        Race race = CharacterWorldTestUtil.createRace();
        when(jdbcTemplate.query(eq("select * from race"), eq(raceRowMapper))).thenReturn(List.of(race));

        List<RaceResponseDTO> responseDTOS = raceService.getAll();

        Assertions.assertAll(
                () -> assertTrue(responseDTOS.isEmpty())
        );
    }

    @Test
    void delete() {
    }
}