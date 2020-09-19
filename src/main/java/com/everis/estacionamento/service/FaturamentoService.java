package com.everis.estacionamento.service;

import com.everis.estacionamento.entity.Faturamento;
import com.everis.estacionamento.entity.Veiculo;
import com.everis.estacionamento.entity.enums.TipoVeiculo;
import com.everis.estacionamento.exceptions.FaturamentoNotFoundException;
import com.everis.estacionamento.repositorys.FaturamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FaturamentoService {

    @Autowired
    private FaturamentoRepository faturamentoRepository;

    public void atualizaFaturamentoAoDarSaida(Veiculo veiculo, double valorHora) {
        Faturamento faturamento = faturamentoRepository.findByData(LocalDate.now());

        if(faturamento == null){
            faturamento = new Faturamento();
            faturamento.setData(LocalDate.now());
//            throw new FaturamentoNotFoundException("Faturamento não encontrado para a data de hoje.");
        }

        double valorFaturamento = 0;

        switch (veiculo.getTipoVeiculo()) {
            case CARRO: {
                valorFaturamento = calcularFaturamentoCarro(valorHora, veiculo);
                faturamento.adicionarValorCarro( valorFaturamento );
            }
                break;
            case MOTO: {
                valorFaturamento = calcularFaturamtnoMoto(valorHora, veiculo);
                faturamento.adicionarValorMoto( valorFaturamento );
            }
                break;
        }

        if(veiculo.getPossuiUtilitario() != null && veiculo.getPossuiUtilitario()){
            faturamento.adicionarValorUtilitario( calcularFaturamentoUtilitarios(valorFaturamento) );
        }

        faturamentoRepository.save(faturamento);
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
