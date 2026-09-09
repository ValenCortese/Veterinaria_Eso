package com.veterinariaEso.Exception;

// RuntimeException: no obliga a declarar throws en los métodos
public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message, Long id) {
    }
}
