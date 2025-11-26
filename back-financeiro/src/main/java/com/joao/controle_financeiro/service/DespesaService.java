package com.joao.controle_financeiro.service;

import com.joao.controle_financeiro.dto.DespesaComparacaoDTO;
import com.joao.controle_financeiro.repository.DespesaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DespesaService {
    private final DespesaRepository repository;

    public DespesaService(DespesaRepository repository){
        this.repository = repository;
    }

    public List<DespesaComparacaoDTO> compararMesAMes(){
        List<Object[]> dados = repository.totalPorMes();

        List<DespesaComparacaoDTO> resultado = new ArrayList<>();

        double totalAnterior = 0;

        for (Object[] obj : dados) {
            int ano = ((Number) obj[0]).intValue();
            int mes = ((Number) obj[1]).intValue();
            double total = ((Number) obj[2]).intValue();

            double diferenca = total - totalAnterior;
            double percentual = (totalAnterior ==0) ? 0 : (diferenca / totalAnterior) *100;

            resultado.add(new DespesaComparacaoDTO(
                    ano, mes, total, diferenca, percentual
            ));
            totalAnterior = total;
        }
        return resultado;
    }
}
