package com.njaga.characterworld.controller;

import com.njaga.characterworld.dto.RaceRequestDTO;
import com.njaga.characterworld.dto.RaceResponseDTO;
import com.njaga.characterworld.dto.SkillRequestDTO;
import com.njaga.characterworld.dto.SkillResponseDTO;
import com.njaga.characterworld.service.RaceService;
import com.njaga.characterworld.service.SkillService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/races")
public class RaceController {

    private final RaceService raceService;

    public RaceController(RaceService raceService) {
        this.raceService = raceService;
    }

    @PostMapping
    public ResponseEntity<RaceResponseDTO> save(@RequestBody RaceRequestDTO requestDTO) {
        return ResponseEntity.ok(raceService.save(requestDTO));
    }

    @GetMapping
    public ResponseEntity<Page<RaceResponseDTO>> getAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(raceService.getAll(pageable));
    }

    @GetMapping("/list")
    public ResponseEntity<List<RaceResponseDTO>> getAll() {
        return ResponseEntity.ok(raceService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RaceResponseDTO> getOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok(raceService.getOne(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RaceResponseDTO> update(@PathVariable("id") Long id, @RequestBody RaceRequestDTO requestDTO) {
        return ResponseEntity.ok(raceService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        raceService.delete(id);
    }
}
