package com.njaga.characterworld.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Date createdDate;
    private Date updatedDate;
}
