package com.eso.domain.exception;

public class DuenioNotFoundException extends RuntimeException {
    public DuenioNotFoundException(Long id) {
        super("No se encontro el duenio con el id " + id);
    }

    public DuenioNotFoundException(String criterio, String valor) {
        super("No se encontro el duenio con " + criterio + " " + valor);
    }
}