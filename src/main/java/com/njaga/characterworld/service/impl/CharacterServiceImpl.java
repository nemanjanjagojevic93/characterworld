package com.njaga.characterworld.service.impl;

import com.njaga.characterworld.dto.CharacterFullResponseDTO;
import com.njaga.characterworld.dto.CharacterLiteResponseDTO;
import com.njaga.characterworld.dto.CharacterRequestDTO;
import com.njaga.characterworld.dto.RaceResponseDTO;
import com.njaga.characterworld.entity.Character;
import com.njaga.characterworld.entity.Race;
import com.njaga.characterworld.exception.ResourceNotFoundException;
import com.njaga.characterworld.repository.CharacterRepository;
import com.njaga.characterworld.service.CharacterService;
import com.njaga.characterworld.service.RaceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;
    private final RaceService raceService;

    public CharacterServiceImpl(CharacterRepository characterRepository, RaceService raceService) {
        this.characterRepository = characterRepository;
        this.raceService = raceService;
    }

    @Override
    public CharacterLiteResponseDTO save(CharacterRequestDTO requestDTO) {
        Character character = new Character();
        character.setName(requestDTO.getName());
        character.setBio(requestDTO.getBio());
        character.setGender(requestDTO.getGender());
        character.setStatus(requestDTO.getStatus());
        RaceResponseDTO raceResponseDTO = raceService.getOne(requestDTO.getRaceId());
        Race race = new Race();
        race.setId(raceResponseDTO.getId());
        character.setRace(race);
        characterRepository.save(character);
        return createCharacterResponseDTO(character, true);
    }

    @Override
    public CharacterLiteResponseDTO update(Long id, CharacterRequestDTO requestDTO) {
        Character character = getOneInternal(id);
        character.setName(requestDTO.getName());
        character.setBio(requestDTO.getBio());
        character.setGender(requestDTO.getGender());
        character.setStatus(requestDTO.getStatus());
        RaceResponseDTO raceResponseDTO = raceService.getOne(requestDTO.getRaceId());
        Race race = new Race();
        race.setId(raceResponseDTO.getId());
        character.setRace(race);
        characterRepository.save(character);
        return createCharacterResponseDTO(character, true);
    }

    @Override
    public CharacterLiteResponseDTO getOne(Long id) {
        Character character = getOneInternal(id);
        return createCharacterResponseDTO(character, true);
    }

    @Override
    public Page<CharacterLiteResponseDTO> getAll(Pageable pageable) {
        Page<Character> characters = characterRepository.findAll(pageable);
        return characters.map(character -> createCharacterResponseDTO(character, false));
    }

    @Override
    public Page<CharacterLiteResponseDTO> getAllByRace(Long raceId, Pageable pageable) {
        Page<Character> characters = characterRepository.findByRaceId(raceId, pageable);
        return characters.map(character -> createCharacterResponseDTO(character, false));
    }

    @Override
    public void delete(Long id) {
        Character character = getOneInternal(id);
        characterRepository.delete(character);
    }

    private Character getOneInternal(Long id) {
        return characterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("characterNotFound"));
    }

    private CharacterLiteResponseDTO createCharacterResponseDTO(Character character, boolean full) {
        if (full) {
            CharacterFullResponseDTO fullResponseDTO = new CharacterFullResponseDTO();
            fullResponseDTO.setId(character.getId());
            fullResponseDTO.setName(character.getName());
            fullResponseDTO.setStatus(character.getStatus());
            return fullResponseDTO;
        }
        CharacterLiteResponseDTO liteResponseDTO = new CharacterLiteResponseDTO();
        liteResponseDTO.setId(character.getId());
        liteResponseDTO.setName(character.getName());
        liteResponseDTO.setStatus(character.getStatus());
        liteResponseDTO.setCreatedDate(character.getCreatedDate());
        liteResponseDTO.setUpdatedDate(character.getUpdatedDate());
        liteResponseDTO.setRace(null);
        return liteResponseDTO;
    }
}
