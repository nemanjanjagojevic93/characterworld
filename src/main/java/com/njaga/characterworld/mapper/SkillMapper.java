package com.njaga.characterworld.mapper;

import com.njaga.characterworld.dto.SkillResponseDTO;
import com.njaga.characterworld.entity.Skill;
import org.springframework.stereotype.Component;

@Component
public class SkillMapper {

    public SkillResponseDTO map(Skill skill) {
        return SkillResponseDTO.builder()
                .id(skill.getId())
                .description(skill.getDescription())
                .createdDate(skill.getCreatedDate())
                .updatedDate(skill.getUpdatedDate())
                .build();
    }
}
