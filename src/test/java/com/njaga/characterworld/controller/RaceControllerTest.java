package com.njaga.characterworld.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.njaga.characterworld.CharacterworldApplication;
import com.njaga.characterworld.dto.RaceRequestDTO;
import com.njaga.characterworld.dto.RaceResponseDTO;
import com.njaga.characterworld.entity.Race;
import com.njaga.characterworld.repository.RaceRepository;
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
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.result.PrintingResultHandler;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = CharacterworldApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class RaceControllerTest {

    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void save() throws Exception {
        RaceRequestDTO requestDTO = CharacterWorldTestUtil.createRaceRequestDTO();

        MvcResult postResult = mvc.perform(post("/races")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andReturn();

        String responseString = postResult.getResponse().getContentAsString();

        RaceResponseDTO postResponseDTO = objectMapper.readValue(responseString, RaceResponseDTO.class);

        MvcResult getResult = mvc.perform(get("/races/" + postResponseDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        responseString = getResult.getResponse().getContentAsString();

        RaceResponseDTO getResponseDTO = objectMapper.readValue(responseString, RaceResponseDTO.class);

        Assertions.assertAll(
                () -> assertNotNull(getResponseDTO.getId()),
                () -> assertEquals("Test race name", getResponseDTO.getName()),
                () -> assertEquals("Test description", getResponseDTO.getDescription()),
                () -> assertNotNull(getResponseDTO.getCreatedDate()),
                () -> assertNotNull(getResponseDTO.getUpdatedDate())
        );

        raceRepository.deleteById(getResponseDTO.getId());
    }

    @Test
    void getAll() throws Exception {

        mvc.perform(get("/races"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getAllList() throws Exception {

        Long raceId = createMockRace();

        MvcResult result = mvc.perform(get("/races/list"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String resultString = result.getResponse().getContentAsString();

        Assertions.assertAll(
                () -> assertTrue(resultString.contains("id")),
                () -> assertTrue(resultString.contains(raceId.toString()))
        );

        raceRepository.deleteById(raceId);
    }

    @Test
    void getOne_NotFound() throws Exception {
        long id = new Random().nextLong();

        mvc.perform(get("/races/" + id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    void update_Ok() throws Exception {
        Long raceId = createMockRace();

        RaceRequestDTO requestDTO = new RaceRequestDTO();
        requestDTO.setName("Updated name");
        requestDTO.setDescription("Updated description");

        MvcResult putResult = mvc.perform(put("/races/" + raceId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andReturn();

        String responseString = putResult.getResponse().getContentAsString();

        RaceResponseDTO putResponseDTO = objectMapper.readValue(responseString, RaceResponseDTO.class);

        Assertions.assertAll(
                () -> assertEquals("Updated name", putResponseDTO.getName()),
                () -> assertEquals("Updated description", putResponseDTO.getDescription()),
                () -> assertNotEquals(putResponseDTO.getCreatedDate(), putResponseDTO.getUpdatedDate())
        );

        MvcResult getResult = mvc.perform(get("/races/" + putResponseDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        responseString = getResult.getResponse().getContentAsString();

        RaceResponseDTO getResponseDTO = objectMapper.readValue(responseString, RaceResponseDTO.class);

        Assertions.assertAll(
                () -> assertEquals("Updated name", getResponseDTO.getName()),
                () -> assertEquals("Updated description", getResponseDTO.getDescription()),
                () -> assertNotEquals(getResponseDTO.getCreatedDate(), getResponseDTO.getUpdatedDate())
        );

        raceRepository.deleteById(raceId);
    }

    @Test
    void update_NotFound() throws Exception {
        long raceId = new Random().nextLong();

        RaceRequestDTO requestDTO = new RaceRequestDTO();
        requestDTO.setName("Updated name");
        requestDTO.setDescription("Updated description");

        mvc.perform(put("/races/" + raceId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_Ok() throws Exception {
        Long raceId = createMockRace();

        mvc.perform(delete("/races/" + raceId))
                .andExpect(status().isNoContent());

        mvc.perform(get("/races/" + raceId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_NotFound() throws Exception {
        long raceId = new Random().nextLong();

        mvc.perform(delete("/races/" + raceId))
                .andExpect(status().isNotFound());
    }


    private Long createMockRace() {
        Race race = new Race();
        race.setName("Test name");
        race.setDescription("Test description");
        return raceRepository.save(race).getId();
    }
}