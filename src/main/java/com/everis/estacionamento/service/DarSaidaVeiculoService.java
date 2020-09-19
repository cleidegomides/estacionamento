package com.everis.estacionamento.service;

import com.everis.estacionamento.dto.VeiculoDto;
import com.everis.estacionamento.entity.Estacionamento;
import com.everis.estacionamento.entity.Veiculo;
import com.everis.estacionamento.exceptions.EstacionamentoNotFoundException;
import com.everis.estacionamento.repositorys.EstacionamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Service
public class DarSaidaVeiculoService {

    @Autowired
    private EstacionamentoRepository estacionamentoRepository;

    @Autowired
    private FaturamentoService faturamentoService;

    @Transactional
    public void darSaidaVeiculo(VeiculoDto veiculoDto){
        Estacionamento estacionamento = estacionamentoRepository.findByDataOperacao(LocalDate.now());

        if( estacionamento == null ){
            throw new EstacionamentoNotFoundException("Estacionamento não encontrado para o dia de hoje");
        }

        Veiculo veiculo = null;

        for(Veiculo v : estacionamento.getVeiculos()){
            if(v.getId().equals(veiculoDto.getId())){
                veiculo = v;
                break;
            }
        }

        veiculo.setHoraSaida(veiculoDto.getHoraSaida());

        faturamentoService.atualizaFaturamentoAoDarSaida(veiculo, estacionamento.getValorHora());

        estacionamento.getVeiculos().remove(veiculo);

        estacionamentoRepository.save(estacionamento);
    }
}
