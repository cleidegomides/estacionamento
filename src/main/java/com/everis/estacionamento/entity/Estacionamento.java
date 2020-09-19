package com.everis.estacionamento.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Estacionamento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private LocalDate dataOperacao;

    private Double valorHora = 0.0;

    @OneToMany
    private List<Veiculo> veiculos = new ArrayList<>();
}
