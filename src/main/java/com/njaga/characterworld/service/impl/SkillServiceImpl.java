package com.njaga.characterworld.service.impl;

import com.njaga.characterworld.dto.SkillRequestDTO;
import com.njaga.characterworld.dto.SkillResponseDTO;
import com.njaga.characterworld.entity.Skill;
import com.njaga.characterworld.exception.BadRequestException;
import com.njaga.characterworld.exception.ResourceNotFoundException;
import com.njaga.characterworld.mapper.SkillMapper;
import com.njaga.characterworld.repository.SkillRepository;
import com.njaga.characterworld.service.SkillService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;

    public SkillServiceImpl(SkillRepository skillRepository, SkillMapper skillMapper) {
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
    }

    @Override
    public SkillResponseDTO save(SkillRequestDTO requestDTO) {
        if (skillRepository.existsByNameIgnoreCase(requestDTO.getName())) {
            throw new BadRequestException("skillNameTaken");
        }
        Skill skill = new Skill();
        skill.setName(requestDTO.getName());
        skill.setDescription(requestDTO.getDescription());
        skillRepository.save(skill);
        return createSkillResponseDTO(skill);
    }

    @Override
    public SkillResponseDTO update(Long id, SkillRequestDTO requestDTO) {
        Skill skill = getOneInternal(id);
        skill.setName(requestDTO.getName());
        skill.setDescription(requestDTO.getDescription());
        skillRepository.save(skill);
        return createSkillResponseDTO(skill);
    }

    @Override
    public SkillResponseDTO getOne(Long id) {
        Skill skill = getOneInternal(id);
        return createSkillResponseDTO(skill);
    }

    @Override
    public SkillResponseDTO getOne(String name) {
        Skill skill = skillRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("skillNotFound"));
        return createSkillResponseDTO(skill);
    }

    @Override
    public void delete(Long id) {
        Skill skill = getOneInternal(id);
        skillRepository.delete(skill);
    }

    @Override
    public Page<SkillResponseDTO> getAll(Pageable pageable) {
        Page<Skill> skills = skillRepository.findAll(pageable);
        return skills.map(this::createSkillResponseDTO);
    }

    private Skill getOneInternal(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("skillNotFound"));
    }

    private SkillResponseDTO createSkillResponseDTO(Skill skill) {
        return SkillResponseDTO.builder()
                .id(skill.getId())
                .name(skill.getName())
                .description(skill.getDescription())
                .createdDate(skill.getCreatedDate())
                .updatedDate(skill.getUpdatedDate())
                .build();
    }
}
