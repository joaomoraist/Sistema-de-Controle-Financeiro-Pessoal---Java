package com.joao.controle_financeiro.controller;

import com.joao.controle_financeiro.model.Receita;
import com.joao.controle_financeiro.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {
    @Autowired
    private ReceitaRepository repo;

    @PostMapping
    public Receita salvar(@RequestBody Receita receita) {
        return repo.save(receita);
    }
}
