package com.njaga.characterworld.service;

import com.njaga.characterworld.dto.CharacterRequestDTO;
import com.njaga.characterworld.dto.SkillRequestDTO;
import com.njaga.characterworld.entity.Skill;
import com.njaga.characterworld.repository.SkillRepository;
import com.njaga.characterworld.service.impl.SkillServiceImpl;
import com.njaga.characterworld.util.CharacterWorldTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

class SkillServiceImplTest {

    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private SkillServiceImpl skillService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save() {
        SkillRequestDTO requestDTO = CharacterWorldTestUtil.createSkillRequestDTO();

        skillService.save(requestDTO);

        verify(skillRepository).save(any(Skill.class));
    }

    @Test
    void update() {
    }

    @Test
    void getOne() {
    }

    @Test
    void delete() {
    }

    @Test
    void getAll() {
    }
}