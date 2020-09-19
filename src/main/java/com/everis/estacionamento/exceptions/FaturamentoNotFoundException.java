package com.everis.estacionamento.exceptions;

public class FaturamentoNotFoundException extends RuntimeException {
    public FaturamentoNotFoundException(String message) {
        super(message);
    }
}
