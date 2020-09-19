package com.everis.estacionamento.service;

import com.everis.estacionamento.entity.Estacionamento;
import com.everis.estacionamento.entity.Veiculo;
import com.everis.estacionamento.exceptions.EstacionamentoNotFoundException;
import com.everis.estacionamento.repositorys.EstacionamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ObterValorTotalVeiculoService {

    @Autowired
    private EstacionamentoRepository estacionamentoRepository;

    public Double obterValorTotalVeiculo(Veiculo veiculo){
        Estacionamento estacionamento = estacionamentoRepository.findByDataOperacao(LocalDate.now());

        if( estacionamento == null ){
            throw new EstacionamentoNotFoundException("Estacionamento não encontrado para o dia de hoje");
        }

        Double valorHora = estacionamento.getValorHora();

        double valorFaturamento = 0;

        switch (veiculo.getTipoVeiculo()) {
            case CARRO: {
                valorFaturamento = calcularFaturamentoCarro(valorHora, veiculo);
            }
            break;
            case MOTO: {
                valorFaturamento = calcularFaturamtnoMoto(valorHora, veiculo);
            }
            break;
        }

        if(veiculo.getPossuiUtilitario() != null && veiculo.getPossuiUtilitario()){
            valorFaturamento += calcularFaturamentoUtilitarios(valorFaturamento);
        }

        return valorFaturamento;
    }

    private double calcularFaturamentoCarro(double valorHora, Veiculo veiculo){
        double valorPorMinuto = valorHora / 60;
        double valorTotalCalculado = valorPorMinuto * veiculo.totalPermanencia();
        return valorTotalCalculado;
    }

    private double calcularFaturamtnoMoto(double valorHora, Veiculo veiculo){
        double valorHoraMoto = valorHora * 0.85; //0,85 = 1 - 0,15(Desconto por ser moto)
        double valorPorMinuto = valorHoraMoto / 60;
        double valorTotalCalculado = valorPorMinuto * veiculo.totalPermanencia();
        return valorTotalCalculado;
    }

    private double calcularFaturamentoUtilitarios(double valorFaturamento){
        return valorFaturamento * 0.15;
    }
}
