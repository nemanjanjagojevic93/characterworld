package com.njaga.characterworld.dto;

import com.njaga.characterworld.enums.Gender;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CharacterFullResponseDTO extends CharacterLiteResponseDTO {
    private String bio;
    private Gender gender;
    private Set<SkillResponseDTO> skills;


}
