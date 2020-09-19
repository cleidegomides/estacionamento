package com.everis.estacionamento.service;

import com.everis.estacionamento.dto.VeiculoDto;
import com.everis.estacionamento.entity.enums.MarcaCarro;
import com.everis.estacionamento.entity.enums.MarcaMoto;
import com.everis.estacionamento.entity.enums.TipoVeiculo;
import com.everis.estacionamento.exceptions.EstacionamentoNotFoundException;
import com.everis.estacionamento.entity.Estacionamento;
import com.everis.estacionamento.entity.Veiculo;
import com.everis.estacionamento.repositorys.EstacionamentoRepository;
import com.everis.estacionamento.repositorys.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class DarEntradaVeiculoService {

    @Autowired
    private EstacionamentoRepository estacionamentoRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Transactional
    public void darEntradaVeiculo(VeiculoDto veiculoDto){
        Estacionamento estacionamento = estacionamentoRepository.findByDataOperacao(LocalDate.now());

        if( estacionamento == null ){
            throw new EstacionamentoNotFoundException("Estacionamento não encontrado para o dia de hoje");
        }

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(veiculoDto.getPlaca());

        if(TipoVeiculo.CARRO.equals(veiculoDto.getTipoVeiculo())){
            veiculo.setMarcaCarro( MarcaCarro.valueOf(veiculoDto.getMarca()) );
        } else {
            veiculo.setMarcaMoto( MarcaMoto.valueOf(veiculoDto.getMarca()) );
        }

        veiculo.setHoraEntrada(veiculoDto.getHoraEntrada());
        veiculo.setModelo(veiculoDto.getModelo());
        veiculo.setPossuiUtilitario( veiculoDto.getUtilitario() ? true : false );
        veiculo.setTipoVeiculo(veiculoDto.getTipoVeiculo());

        veiculoRepository.save(veiculo);

        estacionamento.getVeiculos().add(veiculo);

        estacionamentoRepository.save(estacionamento);
    }
}
