package com.example.demo.api_gateway.dto;

import java.time.LocalDate;

public record SneakerResponse(
    Long id,
    String model,
    LocalDate releaseDate,
    String brand,
    double price,
    int size
) {}
