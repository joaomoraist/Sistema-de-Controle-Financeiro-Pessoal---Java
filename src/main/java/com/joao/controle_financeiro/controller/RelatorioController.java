package com.joao.controle_financeiro.controller;

import com.joao.controle_financeiro.model.relatorio.TotalMesDTO;
import com.joao.controle_financeiro.repository.DespesaRepository;
import com.joao.controle_financeiro.dto.DespesaComparacaoDTO;
import com.joao.controle_financeiro.service.DespesaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final DespesaRepository despesaRepository;
    private final DespesaService despesaService;

    public RelatorioController(DespesaRepository despesaRepository, DespesaService despesaService) {
        this.despesaRepository = despesaRepository;
        this.despesaService = despesaService;
    }

    @GetMapping("/despesas-por-mes")
    public List<TotalMesDTO> despesasPorMes() {
        return despesaRepository.totalPorMes()
                .stream()
                .map(obj -> new TotalMesDTO(
                        ((Double) obj[0]).intValue(),  // ano
                        ((Double) obj[1]).intValue(),  // mes
                        (Double) obj[2]                // total
                ))
                .collect(Collectors.toList());
    }
    @GetMapping("/comparar-meses")
    public List<DespesaComparacaoDTO> compararMeses() {
        return despesaService.compararMesAMes();
    }
}
