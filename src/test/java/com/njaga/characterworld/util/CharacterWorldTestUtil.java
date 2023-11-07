package com.njaga.characterworld.util;

import com.njaga.characterworld.dto.RaceRequestDTO;
import com.njaga.characterworld.dto.SkillRequestDTO;
import com.njaga.characterworld.entity.Race;

import java.util.Date;

public class CharacterWorldTestUtil {

    public static SkillRequestDTO createSkillRequestDTO() {
        SkillRequestDTO requestDTO = new SkillRequestDTO();
        requestDTO.setName("Test skill name");
        requestDTO.setDescription("Test description");
        return requestDTO;
    }

    public static RaceRequestDTO createRaceRequestDTO() {
        RaceRequestDTO requestDTO = new RaceRequestDTO();
        requestDTO.setName("Test race name");
        requestDTO.setDescription("Test description");
        return requestDTO;
    }

    public static Race createRace() {
        Race race = new Race();
        race.setId(1L);
        race.setName("Test race name");
        race.setDescription("Test description");
        race.setCreatedDate(new Date());
        race.setUpdatedDate(new Date());
        return race;
    }
}
