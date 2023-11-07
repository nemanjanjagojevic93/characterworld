package com.njaga.characterworld.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handeResourceNotFoundException(ResourceNotFoundException exception) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("message", exception.getMessage());
        responseMap.put("status", 404);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(responseMap);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handeBadRequestException(BadRequestException exception) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("message", exception.getMessage());
        responseMap.put("status", 400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(responseMap);
    }
}
