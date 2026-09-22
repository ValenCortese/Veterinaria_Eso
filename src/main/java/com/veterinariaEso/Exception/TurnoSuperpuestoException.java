package com.veterinariaEso.Exception;

public class TurnoSuperpuestoException extends RuntimeException {

    public TurnoSuperpuestoException(String message, Long id) {
        super(message + ". Turno conflictivo: ID " + id);
    }

    public TurnoSuperpuestoException(Long id, java.time.LocalDate fecha, java.time.LocalTime hora) {
        super("El veterinario ya tiene el turno ID " + id
                + " el " + fecha + " a las " + hora);
    }
}
