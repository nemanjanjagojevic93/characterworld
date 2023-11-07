package com.njaga.characterworld.service;

import com.njaga.characterworld.dto.SkillRequestDTO;
import com.njaga.characterworld.dto.SkillResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SkillService {

    SkillResponseDTO save(SkillRequestDTO requestDTO);

    SkillResponseDTO update(Long id, SkillRequestDTO requestDTO);

    SkillResponseDTO getOne(Long id);

    SkillResponseDTO getOne(String name);

    void delete(Long id);

    Page<SkillResponseDTO> getAll(Pageable pageable);
}
