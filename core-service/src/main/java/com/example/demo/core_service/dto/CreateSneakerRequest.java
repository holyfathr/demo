package com.example.demo.core_service.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.math.BigDecimal;

@Value
@Builder
public class CreateSneakerRequest {
    String model;
    LocalDate releaseDate;
    String brand;
    BigDecimal price;
    int size;
}
