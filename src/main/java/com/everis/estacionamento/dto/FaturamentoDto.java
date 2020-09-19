package com.everis.estacionamento.dto;

import lombok.Data;

@Data
public class FaturamentoDto {

    private double totalFaturamento = 0;
    private double totalFaturamentoCarro = 0;
    private double totalFaturamentoMoto = 0;
    private double totalFaturamentoUtilitarios = 0;
}
