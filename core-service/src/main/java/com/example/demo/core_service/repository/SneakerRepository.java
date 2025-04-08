package com.example.demo.core_service.repository;

import com.example.demo.core_service.entity.Sneaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SneakerRepository extends JpaRepository<Sneaker, Long> {
}
