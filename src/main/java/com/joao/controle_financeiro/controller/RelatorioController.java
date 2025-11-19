package com.joao.controle_financeiro.controller;

import com.joao.controle_financeiro.model.relatorio.TotalMesDTO;
import com.joao.controle_financeiro.repository.DespesaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final DespesaRepository despesaRepository;

    public RelatorioController(DespesaRepository despesaRepository) {
        this.despesaRepository = despesaRepository;
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
}
