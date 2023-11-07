package com.njaga.characterworld.controller;

import com.njaga.characterworld.dto.SkillRequestDTO;
import com.njaga.characterworld.dto.SkillResponseDTO;
import com.njaga.characterworld.service.SkillService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping
    public ResponseEntity<SkillResponseDTO> save(@RequestBody SkillRequestDTO requestDTO) {
        return ResponseEntity.ok(skillService.save(requestDTO));
    }

    @GetMapping
    public ResponseEntity<Page<SkillResponseDTO>> getAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(skillService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillResponseDTO> getOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok(skillService.getOne(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkillResponseDTO> update(@PathVariable("id") Long id, @RequestBody SkillRequestDTO requestDTO) {
        return ResponseEntity.ok(skillService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        skillService.delete(id);
    }
}
