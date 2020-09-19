package com.everis.estacionamento.service;

import com.everis.estacionamento.entity.Estacionamento;
import com.everis.estacionamento.entity.Veiculo;
import com.everis.estacionamento.exceptions.EstacionamentoNotFoundException;
import com.everis.estacionamento.repositorys.EstacionamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PegarDadosVeiculoService {
    @Autowired
    private EstacionamentoRepository estacionamentoRepository;

    public Veiculo buscarVeiculo(Integer idVeiculo){
        Estacionamento estacionamento = estacionamentoRepository.findByDataOperacao(LocalDate.now());

        if(estacionamento == null){
            throw new EstacionamentoNotFoundException("Estacionamento não existe para o dia de hoje");
        }

        Veiculo veiculo = null;

        for(Veiculo v : estacionamento.getVeiculos()){
            if(v.getId().equals(idVeiculo)){
                veiculo = v;
                break;
            }
        }

        return veiculo;
    }
}
