package com.example.demo.api_gateway.exception;

public class CoreServiceException extends RuntimeException {
    public CoreServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
