package com.everis.estacionamento.exceptions;

public class EstacionamentoNotFoundException extends RuntimeException {
    public EstacionamentoNotFoundException(String message) {
        super(message);
    }
}
