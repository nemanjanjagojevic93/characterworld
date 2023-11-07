package com.njaga.characterworld.service.impl;

import com.njaga.characterworld.dto.RaceRequestDTO;
import com.njaga.characterworld.dto.RaceResponseDTO;
import com.njaga.characterworld.entity.Race;
import com.njaga.characterworld.exception.BadRequestException;
import com.njaga.characterworld.exception.ResourceNotFoundException;
import com.njaga.characterworld.mapper.RaceRowMapper;
import com.njaga.characterworld.repository.RaceRepository;
import com.njaga.characterworld.service.RaceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RaceServiceImpl implements RaceService {

    private final RaceRepository raceRepository;
    private final JdbcTemplate jdbcTemplate;
    private final RaceRowMapper raceRowMapper;

    public RaceServiceImpl(RaceRepository raceRepository, JdbcTemplate jdbcTemplate, RaceRowMapper raceRowMapper) {
        this.raceRepository = raceRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.raceRowMapper = raceRowMapper;
    }

    @Override
    public RaceResponseDTO save(RaceRequestDTO requestDTO) {
        if (raceRepository.existsByNameIgnoreCase(requestDTO.getName())) {
            throw new BadRequestException("raceNameTaken");
        }
        Race race = new Race();
        race.setName(requestDTO.getName());
        race.setDescription(requestDTO.getDescription());
        raceRepository.save(race);
        return createRaceResponseDTO(race);
    }

    @Override
    public RaceResponseDTO update(Long id, RaceRequestDTO requestDTO) {
        Race race = getOneInternal(id);
        race.setName(requestDTO.getName());
        race.setDescription(requestDTO.getDescription());
        raceRepository.save(race);
        return createRaceResponseDTO(race);
    }

    @Override
    public RaceResponseDTO getOne(Long id) {
        Race race = getOneInternal(id);
        return createRaceResponseDTO(race);
    }

    @Override
    public RaceResponseDTO getOne(String name) {
        Race race = raceRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("raceNotFound"));
        return createRaceResponseDTO(race);
    }

    @Override
    public Page<RaceResponseDTO> getAll(Pageable pageable) {
        Page<Race> races = raceRepository.findAll(pageable);
        return races.map(this::createRaceResponseDTO);
    }

    @Override
    public List<RaceResponseDTO> getAll() {
        List<Race> races = jdbcTemplate.query("select * from race", raceRowMapper);
        return races.stream()
                .map(this::createRaceResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Race race = getOneInternal(id);
        raceRepository.delete(race);
    }

    private Race getOneInternal(Long id) {
        return raceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("raceNotFound"));
    }

    private RaceResponseDTO createRaceResponseDTO(Race race) {
        return RaceResponseDTO.builder()
                .id(race.getId())
                .name(race.getName())
                .description(race.getDescription())
                .createdDate(race.getCreatedDate())
                .updatedDate(race.getUpdatedDate())
                .build();
    }
}
