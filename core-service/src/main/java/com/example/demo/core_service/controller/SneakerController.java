package com.example.demo.core_service.controller;

import com.example.demo.core_service.dto.CreateSneakerRequest;
import com.example.demo.core_service.dto.UpdateSneakerRequest;
import com.example.demo.core_service.entity.Sneaker;
import com.example.demo.core_service.service.SneakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sneakers")
@RequiredArgsConstructor
public class SneakerController {

    private final SneakerService sneakerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Sneaker> getSneakers() {
        return sneakerService.getSneakers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Sneaker getSneaker(@PathVariable Long id) {
        return sneakerService.getSneaker(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Sneaker addSneaker(@RequestBody CreateSneakerRequest request) {
        return sneakerService.addSneaker(request);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Sneaker updateSneaker(@RequestBody UpdateSneakerRequest request) {
        return sneakerService.updateSneaker(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSneaker(@PathVariable Long id) {
        sneakerService.deleteSneaker(id);
    }
}
