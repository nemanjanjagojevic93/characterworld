package com.njaga.characterworld.dto;

import com.njaga.characterworld.enums.CharacterStatus;
import com.njaga.characterworld.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.SecondaryTable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacterRequestDTO {
    private String name;
    private String bio;
    private Gender gender;
    private CharacterStatus status;
    private Long raceId;
    private Set<Long> skillIds;
}
