package com.everis.estacionamento.entity;

import com.everis.estacionamento.entity.enums.MarcaCarro;
import com.everis.estacionamento.entity.enums.MarcaMoto;
import com.everis.estacionamento.entity.enums.TipoVeiculo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
public class Veiculo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String placa;

    private TipoVeiculo tipoVeiculo;

    private MarcaCarro marcaCarro;

    private MarcaMoto marcaMoto;

    private String modelo;

    private Boolean possuiUtilitario;

    private LocalTime horaEntrada;

    private LocalTime horaSaida;

    private Double totalEstacionamento = 0.0;

    public Long totalPermanencia(){
        Duration duration = Duration.between(horaEntrada, horaSaida);
        Long minutos = duration.getSeconds() / 60;
        return minutos;
    }
}
