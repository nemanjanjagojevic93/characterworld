package com.njaga.characterworld.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.njaga.characterworld.CharacterworldApplication;
import com.njaga.characterworld.dto.SkillRequestDTO;
import com.njaga.characterworld.dto.SkillResponseDTO;
import com.njaga.characterworld.entity.Skill;
import com.njaga.characterworld.repository.SkillRepository;
import com.njaga.characterworld.util.CharacterWorldTestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = CharacterworldApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class SkillControllerTest {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void save() throws Exception {
        SkillRequestDTO requestDTO = CharacterWorldTestUtil.createSkillRequestDTO();

        MvcResult postResult = mvc.perform(post("/skills")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andReturn();

        String responseString = postResult.getResponse().getContentAsString();

        SkillResponseDTO postResponseDTO = objectMapper.readValue(responseString, SkillResponseDTO.class);

        MvcResult getResult = mvc.perform(get("/skills/" + postResponseDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        responseString = getResult.getResponse().getContentAsString();

        SkillResponseDTO getResponseDTO = objectMapper.readValue(responseString, SkillResponseDTO.class);

        Assertions.assertAll(
                () -> assertNotNull(getResponseDTO.getId()),
                () -> assertEquals("Test skill name", getResponseDTO.getName()),
                () -> assertEquals("Test description", getResponseDTO.getDescription()),
                () -> assertNotNull(getResponseDTO.getCreatedDate()),
                () -> assertNotNull(getResponseDTO.getUpdatedDate())
        );

        skillRepository.deleteById(getResponseDTO.getId());
    }

    @Test
    void getAll() throws Exception {

        mvc.perform(get("/skills"))
                .andExpect(status().isOk());
    }

    @Test
    void getOne_NotFound() throws Exception {
        long id = new Random().nextLong();

        mvc.perform(get("/skills/" + id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    void update_Ok() throws Exception {
        Long skillId = createMockSkill();

        SkillRequestDTO requestDTO = new SkillRequestDTO();
        requestDTO.setName("Updated name");
        requestDTO.setDescription("Updated description");

        MvcResult putResult = mvc.perform(put("/skills/" + skillId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andReturn();

        String responseString = putResult.getResponse().getContentAsString();

        SkillResponseDTO putResponseDTO = objectMapper.readValue(responseString, SkillResponseDTO.class);

        Assertions.assertAll(
                () -> assertEquals("Updated name", putResponseDTO.getName()),
                () -> assertEquals("Updated description", putResponseDTO.getDescription()),
                () -> assertNotEquals(putResponseDTO.getCreatedDate(), putResponseDTO.getUpdatedDate())
        );

        MvcResult getResult = mvc.perform(get("/skills/" + putResponseDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        responseString = getResult.getResponse().getContentAsString();

        SkillResponseDTO getResponseDTO = objectMapper.readValue(responseString, SkillResponseDTO.class);

        Assertions.assertAll(
                () -> assertEquals("Updated name", getResponseDTO.getName()),
                () -> assertEquals("Updated description", getResponseDTO.getDescription()),
                () -> assertNotEquals(getResponseDTO.getCreatedDate(), getResponseDTO.getUpdatedDate())
        );

        skillRepository.deleteById(skillId);
    }

    @Test
    void update_NotFound() throws Exception {
        long skillId = new Random().nextLong();

        SkillRequestDTO requestDTO = new SkillRequestDTO();
        requestDTO.setName("Updated name");
        requestDTO.setDescription("Updated description");

        mvc.perform(put("/skills/" + skillId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_Ok() throws Exception {
        Long skillId = createMockSkill();

        mvc.perform(delete("/skills/" + skillId))
                .andExpect(status().isNoContent());

        mvc.perform(get("/skills/" + skillId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_NotFound() throws Exception {
        long skillId = new Random().nextLong();

        mvc.perform(delete("/skills/" + skillId))
                .andExpect(status().isNotFound());
    }


    private Long createMockSkill() {
        Skill skill = new Skill();
        skill.setName("Test name");
        skill.setDescription("Test description");
        return skillRepository.save(skill).getId();
    }
}