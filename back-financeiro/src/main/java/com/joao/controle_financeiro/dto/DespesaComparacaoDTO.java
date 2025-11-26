package com.joao.controle_financeiro.dto;

public record DespesaComparacaoDTO(
        int ano,
        int mes,
        double total,
        double diferenca,
        double percentual
) {}
