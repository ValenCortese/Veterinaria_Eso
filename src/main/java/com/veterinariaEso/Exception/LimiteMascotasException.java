package com.veterinariaEso.Exception;

public class LimiteMascotasException extends RuntimeException {

    public LimiteMascotasException(Long duenioId, long cantidadActual) {
        super("El dueño con ID " + duenioId + " ya tiene " + cantidadActual
                + " mascotas activas. No puede registrar más de 5.");
    }
}
