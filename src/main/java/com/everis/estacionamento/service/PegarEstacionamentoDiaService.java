package com.everis.estacionamento.service;

import com.everis.estacionamento.entity.Estacionamento;
import com.everis.estacionamento.exceptions.EstacionamentoNotFoundException;
import com.everis.estacionamento.repositorys.EstacionamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PegarEstacionamentoDiaService {

    @Autowired
    private EstacionamentoRepository estacionamentoRepository;

    public Estacionamento pegaEstacionamentoDia(){
        Estacionamento estacionamento = estacionamentoRepository.findByDataOperacao(LocalDate.now());

        if(estacionamento == null){
            throw new EstacionamentoNotFoundException("Estacionamento não existe para o dia de hoje");
        }

        return estacionamento;
    }
}
