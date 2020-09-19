package com.everis.estacionamento.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class EstacionamentoDto {

    @NotNull
    private double valorhora;
}
