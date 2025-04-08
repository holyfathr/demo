package com.example.demo.api_gateway.service;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.example.demo.api_gateway.dto.CreateSneakerRequest;
import com.example.demo.api_gateway.dto.SneakerResponse;
import com.example.demo.api_gateway.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@WireMockTest(httpPort = 8081)
class SneakerServiceTest {

    @Autowired
    private SneakerService sneakerService;

    @Test
    @DisplayName("getSneakers_ShouldReturnListOfSneakers_WhenDataExists")
    void getSneakers_ShouldReturnListOfSneakers_WhenDataExists() {
        String responseBody = "[{\"id\":1,\"model\":\"Nike Air Max\",\"releaseDate\":\"2023-01-01\",\"brand\":\"Nike\",\"price\":199.99,\"size\":42}]";
        stubFor(get("/api/sneakers")
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));

        List<SneakerResponse> result = sneakerService.getSneakers();

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).model()).isEqualTo("Nike Air Max");
    }

    @Test
    @DisplayName("getSneaker_ShouldReturnSneaker_WhenItExists")
    void getSneaker_ShouldReturnSneaker_WhenItExists() {
        String responseBody = "{\"id\":1,\"model\":\"Nike Air Max\",\"releaseDate\":\"2023-01-01\",\"brand\":\"Nike\",\"price\":199.99,\"size\":42}";
        stubFor(get("/api/sneakers/1")
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));

        SneakerResponse result = sneakerService.getSneaker(1L);

        assertThat(result).isNotNull();
        assertThat(result.model()).isEqualTo("Nike Air Max");
    }

    @Test
    @DisplayName("addSneaker_ShouldAddSneaker_WhenDataIsValid")
    void addSneaker_ShouldAddSneaker_WhenDataIsValid() {
        CreateSneakerRequest request = new CreateSneakerRequest("Nike Air Max", "2023-01-01", "Nike", 199.99, 42);
        String requestBody = "{\"model\":\"Nike Air Max\",\"releaseDate\":\"2023-01-01\",\"brand\":\"Nike\",\"price\":199.99,\"size\":42}";
        String responseBody = "{\"id\":1,\"model\":\"Nike Air Max\",\"releaseDate\":\"2023-01-01\",\"brand\":\"Nike\",\"price\":199.99,\"size\":42}";

        stubFor(post("/api/sneakers")
                .withRequestBody(equalToJson(requestBody))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));

        SneakerResponse result = sneakerService.addSneaker(request);

        assertThat(result).isNotNull();
        assertThat(result.model()).isEqualTo("Nike Air Max");
    }

    @Test
    @DisplayName("deleteSneaker_ShouldThrowNotFoundException_WhenSneakerDoesNotExist")
    void deleteSneaker_ShouldThrowNotFoundException_WhenSneakerDoesNotExist() {
        stubFor(delete("/api/sneakers/99")
                .willReturn(WireMock.aResponse().withStatus(404)));

        assertThatThrownBy(() -> sneakerService.deleteSneaker(99L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Sneaker with ID 99 not found");
    }
}
