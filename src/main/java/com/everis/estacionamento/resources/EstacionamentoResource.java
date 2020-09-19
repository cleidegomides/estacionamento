package com.everis.estacionamento.resources;

import com.everis.estacionamento.dto.VeiculoDto;
import com.everis.estacionamento.entity.Estacionamento;
import com.everis.estacionamento.entity.Veiculo;
import com.everis.estacionamento.entity.enums.MarcaCarro;
import com.everis.estacionamento.entity.enums.MarcaMoto;
import com.everis.estacionamento.entity.enums.TipoVeiculo;
import com.everis.estacionamento.service.PegarEstacionamentoDiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/estacionamento")
public class EstacionamentoResource {

    @Autowired
    private PegarEstacionamentoDiaService pegarEstacionamentoDiaService;

    @GetMapping("/dados/veiculos")
    public List<VeiculoDto> getVeiculosDoEstacionamento(){
        Estacionamento estacionamento = pegarEstacionamentoDiaService.pegaEstacionamentoDia();

        List<VeiculoDto> veiculoDtos = estacionamento
                .getVeiculos()
                .stream()
                .map(veiculo -> {
                    VeiculoDto veiculoDto = new VeiculoDto();
                    veiculoDto.setId(veiculo.getId());
                    veiculoDto.setHoraEntrada(veiculo.getHoraEntrada());

                    if(TipoVeiculo.CARRO.equals(veiculo.getTipoVeiculo())){
                        veiculoDto.setMarca( veiculo.getMarcaCarro().toString() );
                    } else {
                        veiculoDto.setMarca( veiculo.getMarcaMoto().toString() );
                    }

                    veiculoDto.setModelo(veiculo.getModelo());
                    veiculoDto.setPlaca(veiculo.getPlaca());
                    veiculoDto.setTipoVeiculo(veiculo.getTipoVeiculo());
                    veiculoDto.setUtilitario(veiculo.getPossuiUtilitario());
                    veiculoDto.setHoraSaida(veiculo.getHoraSaida());
                    return veiculoDto;
                })
                .collect(Collectors.toList());

        return veiculoDtos;
    }

    @GetMapping("/dados/marcas")
    public Object[] opcaoMarcaCarroMoto(String tipoVeiculo){

        if(TipoVeiculo.CARRO.toString().equalsIgnoreCase(tipoVeiculo)){
            return Arrays.stream(MarcaCarro.values()).toArray();
        } else{
            return Arrays.stream(MarcaMoto.values()).toArray();
        }

    }
}
