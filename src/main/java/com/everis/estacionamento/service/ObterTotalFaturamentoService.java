package com.everis.estacionamento.service;

import com.everis.estacionamento.entity.Faturamento;
import com.everis.estacionamento.repositorys.FaturamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ObterTotalFaturamentoService {

    @Autowired
    private FaturamentoRepository faturamentoRepository;


    public Faturamento oberterFaturamentoTotal() {
        Faturamento faturamento = faturamentoRepository.findByData(LocalDate.now());


        if (faturamento == null) {
            faturamento = new Faturamento();
        }

        return faturamento;
    }
}
