package com.veterinariaEso.Exception;

// RuntimeException: no obliga a declarar throws en los métodos
public class TurnoSuperpuestoException extends RuntimeException {
    public TurnoSuperpuestoException(String message, Long id) {
    }
}
