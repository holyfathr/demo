package com.example.demo.core_service.service;

import com.example.demo.core_service.dto.CreateSneakerRequest;
import com.example.demo.core_service.dto.UpdateSneakerRequest;
import com.example.demo.core_service.entity.Sneaker;
import com.example.demo.core_service.exception.NotFoundException;
import com.example.demo.core_service.repository.SneakerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SneakerService {

    private final SneakerRepository sneakerRepository;

    public List<Sneaker> getSneakers() {
        return sneakerRepository.findAll();
    }

    public Sneaker getSneaker(Long id) {
        return sneakerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Кроссовки с id " + id + " не найдены"));
    }

    @Transactional
    public Sneaker addSneaker(CreateSneakerRequest request) {
        Sneaker sneaker = Sneaker.builder()
                .model(request.getModel())
                .releaseDate(request.getReleaseDate())
                .brand(request.getBrand())
                .price(request.getPrice())
                .size(request.getSize())
                .build();
        return sneakerRepository.save(sneaker);
    }

    @Transactional
    public Sneaker updateSneaker(UpdateSneakerRequest request) {
        Sneaker sneaker = getSneaker(request.getId());
        sneaker.setModel(request.getModel());
        sneaker.setReleaseDate(request.getReleaseDate());
        sneaker.setBrand(request.getBrand());
        sneaker.setPrice(request.getPrice());
        sneaker.setSize(request.getSize());
        return sneakerRepository.save(sneaker);
    }

    @Transactional
    public void deleteSneaker(Long id) {
        if (!sneakerRepository.existsById(id)) {
            throw new NotFoundException("Кроссовки с id " + id + " не найдены");
        }
        sneakerRepository.deleteById(id);
    }
}
