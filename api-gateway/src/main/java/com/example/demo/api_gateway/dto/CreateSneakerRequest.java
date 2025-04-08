package com.example.demo.api_gateway.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record CreateSneakerRequest(
    String model,
    LocalDate releaseDate,
    String brand,
    double price,
    int size
) {}
