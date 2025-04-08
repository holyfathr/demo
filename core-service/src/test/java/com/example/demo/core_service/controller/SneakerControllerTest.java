package com.example.demo.core_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.example.demo.core_service.dto.CreateSneakerRequest;
import com.example.demo.core_service.dto.UpdateSneakerRequest;
import com.example.demo.core_service.entity.Sneaker;
import com.example.demo.core_service.service.SneakerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        // given
        List<Sneaker> sneakers = List.of(
            Sneaker.builder()
                .id(1L)
                .model("Nike Air Max")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .brand("Nike")
                .price(new BigDecimal("199.99"))
                .size(42)
                .build()
        );
        when(sneakerService.getSneakers()).thenReturn(sneakers);

        // when/then
        mockMvc.perform(get("/api/sneakers"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(sneakers)));
    }

    @Test
    @DisplayName("getSneaker_ShouldReturnSneaker_WhenItExists")
    void getSneaker_ShouldReturnSneaker_WhenItExists() throws Exception {
        // given
        Sneaker sneaker = Sneaker.builder()
                .id(1L)
                .model("Nike Air Max")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .brand("Nike")
                .price(new BigDecimal("199.99"))
                .size(42)
                .build();
        when(sneakerService.getSneaker(1L)).thenReturn(sneaker);

        // when/then
        mockMvc.perform(get("/api/sneakers/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(sneaker)));
    }

    @Test
    @DisplayName("addSneaker_ShouldAddSneaker_WhenDataIsValid")
    void addSneaker_ShouldAddSneaker_WhenDataIsValid() throws Exception {
        // given
        CreateSneakerRequest request = CreateSneakerRequest.builder()
                .model("Nike Air Max")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .brand("Nike")
                .price(new BigDecimal("199.99"))
                .size(42)
                .build();
        
        Sneaker sneaker = Sneaker.builder()
                .id(1L)
                .model("Nike Air Max")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .brand("Nike")
                .price(new BigDecimal("199.99"))
                .size(42)
                .build();
                
        when(sneakerService.addSneaker(any(CreateSneakerRequest.class))).thenReturn(sneaker);

        // when/then
        mockMvc.perform(post("/api/sneakers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(sneaker)));
    }

    @Test
    @DisplayName("updateSneaker_ShouldUpdateSneaker_WhenDataIsValid")
    void updateSneaker_ShouldUpdateSneaker_WhenDataIsValid() throws Exception {
        // given
        UpdateSneakerRequest request = UpdateSneakerRequest.builder()
                .id(1L)
                .model("Updated Nike Air Max")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .brand("Nike")
                .price(new BigDecimal("249.99"))
                .size(42)
                .build();

        Sneaker updatedSneaker = Sneaker.builder()
                .id(1L)
                .model("Updated Nike Air Max")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .brand("Nike")
                .price(new BigDecimal("249.99"))
                .size(42)
                .build();

        when(sneakerService.updateSneaker(any(UpdateSneakerRequest.class))).thenReturn(updatedSneaker);

        // when/then
        mockMvc.perform(put("/api/sneakers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedSneaker)));
    }

    @Test
    @DisplayName("deleteSneaker_ShouldDeleteSneaker_WhenItExists")
    void deleteSneaker_ShouldDeleteSneaker_WhenItExists() throws Exception {
        // given
        doNothing().when(sneakerService).deleteSneaker(1L);

        // when/then
        mockMvc.perform(delete("/api/sneakers/1"))
                .andExpect(status().isOk());

        verify(sneakerService, times(1)).deleteSneaker(1L);
    }
}
