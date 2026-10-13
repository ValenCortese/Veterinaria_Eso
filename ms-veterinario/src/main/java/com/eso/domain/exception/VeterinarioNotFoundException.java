package com.eso.domain.exception;

public class VeterinarioNotFoundException extends RuntimeException {
    public VeterinarioNotFoundException(String message) {
        super(message);
    }
}
