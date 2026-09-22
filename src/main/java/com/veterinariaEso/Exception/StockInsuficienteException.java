package com.veterinariaEso.Exception;

public class StockInsuficienteException extends RuntimeException {

    public StockInsuficienteException(Long medicamentoId) {
        super("No hay stock disponible para el medicamento con ID " + medicamentoId);
    }
}
