package com.everis.estacionamento.dto;

import com.everis.estacionamento.entity.enums.MarcaCarro;
import com.everis.estacionamento.entity.enums.MarcaMoto;
import com.everis.estacionamento.entity.enums.TipoVeiculo;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Data
public class VeiculoDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    //@NotBlank
    private String placa;

    //@NotNull
    private TipoVeiculo tipoVeiculo;

    private String marca;

    //@NotBlank
    private String modelo;

    private Boolean utilitario = false;

    //@NotNull
    private LocalTime horaEntrada;


    private LocalTime horaSaida;


    private Double valor;
}
