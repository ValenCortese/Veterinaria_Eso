package com.eso.adapter.in.web;

import com.eso.domain.exception.VeterinarioNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(VeterinarioNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleVeterinarioNotFoundException(VeterinarioNotFoundException ex,
                                                                            HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                           HttpServletRequest request) {
        String detalle = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return build(HttpStatus.BAD_REQUEST, detalle, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex,
                                                         HttpServletRequest request) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request);
    }

    private ResponseEntity<ErrorResponse> build(HttpStatus status, String mensaje,
                                                HttpServletRequest request) {
        ErrorResponse body = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                mensaje,
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(body);
    }
}