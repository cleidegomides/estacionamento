package com.everis.estacionamento.repositorys;

import com.everis.estacionamento.entity.Faturamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface FaturamentoRepository extends JpaRepository<Faturamento, Integer> {
    public Faturamento findByData(LocalDate data);
}
