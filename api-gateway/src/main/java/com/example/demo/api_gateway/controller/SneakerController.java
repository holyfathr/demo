package com.example.demo.api_gateway.controller;

import com.example.demo.api_gateway.dto.CreateSneakerRequest;
import com.example.demo.api_gateway.dto.SneakerResponse;
import com.example.demo.api_gateway.dto.UpdateSneakerRequest;
import com.example.demo.api_gateway.service.SneakerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sneakers")
@RequiredArgsConstructor
@Tag(name = "Кроссовки", description = "Эндпоинты для работы с кроссовками")
public class SneakerController {

    private final SneakerService sneakerService;

    @Operation(summary = "Получить список кроссовок")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список кроссовок успешно получен"),
            @ApiResponse(responseCode = "502", description = "Ошибка внешнего сервиса"),
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SneakerResponse> getSneakers() {
        return sneakerService.getSneakers();
    }

    @Operation(summary = "Получить кроссовки по ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Кроссовки найдены"),
            @ApiResponse(responseCode = "404", description = "Кроссовки не найдены"),
            @ApiResponse(responseCode = "502", description = "Ошибка внешнего сервиса")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SneakerResponse getSneaker(@PathVariable Long id) {
        return sneakerService.getSneaker(id);
    }

    @Operation(summary = "Добавить новые кроссовки")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Кроссовки успешно добавлены"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса"),
            @ApiResponse(responseCode = "502", description = "Ошибка внешнего сервиса")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SneakerResponse addSneaker(@RequestBody CreateSneakerRequest request) {
        return sneakerService.addSneaker(request);
    }

    @Operation(summary = "Обновить данные кроссовок")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Данные кроссовок обновлены"),
            @ApiResponse(responseCode = "404", description = "Кроссовки не найдены"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса"),
            @ApiResponse(responseCode = "502", description = "Ошибка внешнего сервиса")
    })
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public SneakerResponse updateSneaker(@RequestBody UpdateSneakerRequest request) {
        return sneakerService.updateSneaker(request);
    }

    @Operation(summary = "Удалить кроссовки по ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Кроссовки удалены"),
            @ApiResponse(responseCode = "404", description = "Кроссовки не найдены"),
            @ApiResponse(responseCode = "502", description = "Ошибка внешнего сервиса")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSneaker(@PathVariable Long id) {
        sneakerService.deleteSneaker(id);
    }
}
