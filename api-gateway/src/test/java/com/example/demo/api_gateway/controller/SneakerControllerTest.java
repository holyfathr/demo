package com.example.demo.api_gateway.controller;

import com.example.demo.api_gateway.dto.CreateSneakerRequest;
import com.example.demo.api_gateway.dto.SneakerResponse;
import com.example.demo.api_gateway.dto.UpdateSneakerRequest;
import com.example.demo.api_gateway.service.SneakerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SneakerControllerTest {
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @Mock
    private SneakerService sneakerService;

    @InjectMocks
    private SneakerController sneakerController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(sneakerController).build();
    }

    @Test
    @DisplayName("getSneakers_ShouldReturnListOfSneakers_WhenDataExists")
    void getSneakers_ShouldReturnListOfSneakers_WhenDataExists() throws Exception {
        List<SneakerResponse> sneakers = List.of(
            new SneakerResponse(1L, "Nike Air Max", LocalDate.of(2023, 1, 1), "Nike", 199.99, 42)
        );
        when(sneakerService.getSneakers()).thenReturn(sneakers);

        mockMvc.perform(get("/api/sneakers"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(sneakers)));
    }

    @Test
    @DisplayName("getSneaker_ShouldReturnSneaker_WhenItExists")
    void getSneaker_ShouldReturnSneaker_WhenItExists() throws Exception {
        SneakerResponse sneaker = new SneakerResponse(1L, "Nike Air Max", LocalDate.of(2023, 1, 1), "Nike", 199.99, 42);
        when(sneakerService.getSneaker(1L)).thenReturn(sneaker);

        mockMvc.perform(get("/api/sneakers/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(sneaker)));
    }

    @Test
    @DisplayName("addSneaker_ShouldAddSneaker_WhenDataIsValid")
    void addSneaker_ShouldAddSneaker_WhenDataIsValid() throws Exception {
        CreateSneakerRequest request = new CreateSneakerRequest("Nike Air Max", LocalDate.of(2023, 1, 1), "Nike", 199.99, 42);
        SneakerResponse sneaker = new SneakerResponse(1L, "Nike Air Max", LocalDate.of(2023, 1, 1), "Nike", 199.99, 42);
        when(sneakerService.addSneaker(any(CreateSneakerRequest.class))).thenReturn(sneaker);

        mockMvc.perform(post("/api/sneakers")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(sneaker)));
    }

    @Test
    @DisplayName("updateSneaker_ShouldUpdateSneaker_WhenDataIsValid")
    void updateSneaker_ShouldUpdateSneaker_WhenDataIsValid() throws Exception {
        UpdateSneakerRequest request = new UpdateSneakerRequest(1L, "Updated Nike Air Max", LocalDate.of(2023, 1, 1), "Nike", 249.99, 42);
        SneakerResponse sneaker = new SneakerResponse(1L, "Updated Nike Air Max", LocalDate.of(2023, 1, 1), "Nike", 249.99, 42);
        when(sneakerService.updateSneaker(any(UpdateSneakerRequest.class))).thenReturn(sneaker);

        mockMvc.perform(put("/api/sneakers")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(sneaker)));
    }

    @Test
    @DisplayName("deleteSneaker_ShouldDeleteSneaker_WhenItExists")
    void deleteSneaker_ShouldDeleteSneaker_WhenItExists() throws Exception {
        mockMvc.perform(delete("/api/sneakers/1"))
                .andExpect(status().isOk());
    }
}
