package com.njaga.characterworld.service;

import com.njaga.characterworld.dto.CharacterLiteResponseDTO;
import com.njaga.characterworld.dto.CharacterRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CharacterService {

    CharacterLiteResponseDTO save(CharacterRequestDTO requestDTO);

    CharacterLiteResponseDTO update(Long id, CharacterRequestDTO requestDTO);

    CharacterLiteResponseDTO getOne(Long id);

    Page<CharacterLiteResponseDTO> getAll(Pageable pageable);

    Page<CharacterLiteResponseDTO> getAllByRace(Long raceId, Pageable pageable);

    void delete(Long id);
}
