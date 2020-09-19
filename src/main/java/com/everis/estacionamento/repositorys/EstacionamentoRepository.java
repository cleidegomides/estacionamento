package com.everis.estacionamento.repositorys;

import com.everis.estacionamento.entity.Estacionamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface EstacionamentoRepository extends JpaRepository<Estacionamento, Integer> {
    public Estacionamento findByDataOperacao(LocalDate data);
}
