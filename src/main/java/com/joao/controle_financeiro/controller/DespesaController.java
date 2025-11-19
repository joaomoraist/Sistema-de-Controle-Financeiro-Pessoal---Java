package com.joao.controle_financeiro.controller;

import com.joao.controle_financeiro.model.Despesa;
import com.joao.controle_financeiro.repository.DespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/despesas")
public class DespesaController {
    @Autowired
    private DespesaRepository repo;

    @PostMapping
    public Despesa salvar(@RequestBody Despesa despesa) {
        return repo.save(despesa);
    }
}
