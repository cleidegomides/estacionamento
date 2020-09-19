package com.everis.estacionamento.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
public class Faturamento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private LocalDate data;

    private double totalFaturamento = 0;
    private double totalFaturamentoCarro = 0;
    private double totalFaturamentoMoto = 0;
    private double totalFaturamentoUtilitarios = 0;

    private void adicionarValorTotal(double valor){
        this.totalFaturamento += valor;
    }

    public void adicionarValorCarro(double valor){
        this.totalFaturamentoCarro += valor;
        adicionarValorTotal(valor);
    }

    public void adicionarValorMoto(double valor){
        this.totalFaturamentoMoto += valor;
        adicionarValorTotal(valor);
    }

    public void adicionarValorUtilitario(double valor){
        this.totalFaturamentoUtilitarios += valor;
        adicionarValorTotal(valor);
    }
}
