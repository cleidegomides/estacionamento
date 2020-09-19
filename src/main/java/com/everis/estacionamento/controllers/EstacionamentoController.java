package com.everis.estacionamento.controllers;

import com.everis.estacionamento.dto.EstacionamentoDto;
import com.everis.estacionamento.dto.FaturamentoDto;
import com.everis.estacionamento.dto.VeiculoDto;
import com.everis.estacionamento.entity.Estacionamento;
import com.everis.estacionamento.entity.Faturamento;
import com.everis.estacionamento.entity.Veiculo;
import com.everis.estacionamento.entity.enums.MarcaCarro;
import com.everis.estacionamento.entity.enums.MarcaMoto;
import com.everis.estacionamento.entity.enums.TipoVeiculo;
import com.everis.estacionamento.service.*;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.swing.text.html.parser.Entity;
import javax.validation.Valid;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/estacionamento")
public class EstacionamentoController {

    @Autowired
    private CriarEstacionamentoService criarEstacionamentoService;

    @Autowired
    private PegarEstacionamentoDiaService pegarEstacionamentoDiaService;

    @Autowired
    private DarEntradaVeiculoService darEntradaVeiculoService;

    @Autowired
    private DarSaidaVeiculoService darSaidaVeiculoService;

    @Autowired
    private PegarDadosVeiculoService pegarDadosVeiculoService;

    @Autowired
    private ObterValorTotalVeiculoService obterValorTotalVeiculoService;

    @Autowired
    private ObterTotalFaturamentoService obterTotalFaturamentoService;

    @GetMapping
    public ModelAndView paginaPrincipal(){
        return new ModelAndView("estacionamento");
    }

    @GetMapping("/dados")
    public ModelAndView listaVeiculos(){
        Estacionamento estacionamento = pegarEstacionamentoDiaService.pegaEstacionamentoDia();

        ModelAndView mv = new ModelAndView("dadosestacionamento");
        mv.addObject("estacionamento", estacionamento);
        return mv;
    }

    @PostMapping
    public String salvarEstacionamento(@Valid EstacionamentoDto estacionamentoDto,
                                       BindingResult result, RedirectAttributes attributes){
        if (result.hasErrors()){
            attributes.addFlashAttribute("mensagem","Campo obrigatório não preenchido!");
            return "redirect:/estacionamento";
        }

        criarEstacionamentoService.criarEstacionamento(estacionamentoDto);
        return "redirect:/estacionamento/dados";
    }

    @GetMapping("/cadastro")
    public ModelAndView  cadastrarVeiculos(@RequestParam("tipoVeiculo") String tipoVeiculo){
        ModelAndView mv;
        mv = new ModelAndView("cadastroveiculo");
        mv.addObject("tipoVeiculo", TipoVeiculo.values());
        mv.addObject("marcaCarro", MarcaCarro.values());
        mv.addObject("marcaMoto", MarcaMoto.values());
        return mv;
    }

    @PostMapping("/veiculonovo")
    public String salvarNovoVeiculo(@Valid VeiculoDto veiculoDto,
                                    BindingResult result, RedirectAttributes attributes){
        if (result.hasErrors()){
            attributes.addFlashAttribute("mensagem","Campo obrigatório não preenchido!");
            return "redirect:/estacionamento/cadastro";
        }
        darEntradaVeiculoService.darEntradaVeiculo(veiculoDto);

        return "redirect:/estacionamento/dados";
    }

    @GetMapping("/detalhessaidaveiculo/{idveiculo}")
    public ModelAndView detalhesSaidaVeiculo(@PathVariable("idveiculo") Integer idveiculo){
        Veiculo veiculo = pegarDadosVeiculoService.buscarVeiculo(idveiculo);
        veiculo.setHoraSaida(LocalTime.now());

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
        veiculoDto.setValor(obterValorTotalVeiculoService.obterValorTotalVeiculo(veiculo));

        ModelAndView mv = new ModelAndView("detalhessaidaveiculo");
        mv.addObject("veiculo", veiculoDto);
        return mv;
    }

    @PostMapping("/darsaida")
    public String darSaidaVeiculo(VeiculoDto veiculoDto){
        darSaidaVeiculoService.darSaidaVeiculo(veiculoDto);
        return "redirect:/estacionamento/dados";
    }

    @GetMapping("/detalhefaturamento")
    public ModelAndView detalheFaturamento(){
        Faturamento faturamento = obterTotalFaturamentoService.oberterFaturamentoTotal();

        FaturamentoDto faturamentoDto = new FaturamentoDto();
        faturamentoDto.setTotalFaturamentoCarro(faturamento.getTotalFaturamentoCarro());
        faturamentoDto.setTotalFaturamentoMoto(faturamento.getTotalFaturamentoMoto());
        faturamentoDto.setTotalFaturamentoUtilitarios(faturamento.getTotalFaturamentoUtilitarios());
        faturamentoDto.setTotalFaturamento(faturamento.getTotalFaturamento());

        ModelAndView mv = new ModelAndView("detalhefaturamento");
        mv.addObject("faturamento",faturamentoDto);
        return mv;
    }
}
