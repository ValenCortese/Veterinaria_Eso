package com.eso.domain.exception;

public class MascotaNotFoundException extends RuntimeException {
    public MascotaNotFoundException(String message) {
        super(message);
    }
}
