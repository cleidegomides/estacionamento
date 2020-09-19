package com.everis.estacionamento.service;

import com.everis.estacionamento.dto.EstacionamentoDto;
import com.everis.estacionamento.entity.Estacionamento;
import com.everis.estacionamento.repositorys.EstacionamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CriarEstacionamentoService {

    @Autowired
    private EstacionamentoRepository estacionamentoRepository;

    public Estacionamento criarEstacionamento(EstacionamentoDto estacionamentoDto){

        Estacionamento estacionamento = estacionamentoRepository.findByDataOperacao(LocalDate.now());

        if (estacionamento == null){
            estacionamento = new Estacionamento();
            estacionamento.setDataOperacao(LocalDate.now());
        }

        if ( !estacionamento.getValorHora().equals(estacionamentoDto.getValorhora()) ) {
            estacionamento.setValorHora(estacionamentoDto.getValorhora());
        }
        estacionamentoRepository.save(estacionamento);
        return estacionamento;
    }

}
