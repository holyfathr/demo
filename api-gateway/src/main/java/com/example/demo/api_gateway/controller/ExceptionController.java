package com.example.demo.api_gateway.controller;

import com.example.demo.api_gateway.exception.CoreServiceException;
import com.example.demo.api_gateway.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CoreServiceException.class)
    public ResponseEntity<String> handleCoreServiceException(CoreServiceException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_GATEWAY);
    }
}
