package com.njaga.characterworld.dto;

import com.njaga.characterworld.enums.CharacterStatus;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CharacterLiteResponseDTO {
    private Long id;
    private String name;
    private CharacterStatus status;
    private RaceResponseDTO race;
    private Date createdDate;
    private Date updatedDate;
}
