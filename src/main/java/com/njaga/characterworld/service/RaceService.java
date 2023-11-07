package com.njaga.characterworld.service;

import com.njaga.characterworld.dto.RaceRequestDTO;
import com.njaga.characterworld.dto.RaceResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RaceService {

    RaceResponseDTO save(RaceRequestDTO requestDTO);

    RaceResponseDTO update(Long id, RaceRequestDTO requestDTO);

    RaceResponseDTO getOne(Long id);

    RaceResponseDTO getOne(String name);

    Page<RaceResponseDTO> getAll(Pageable pageable);

    List<RaceResponseDTO> getAll();

    void delete(Long id);
}
