package com.example.demo.core_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sneaker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "size", nullable = false)
    private int size;
}
