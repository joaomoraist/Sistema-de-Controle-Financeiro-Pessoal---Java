package com.joao.controle_financeiro.repository;

import com.joao.controle_financeiro.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface DespesaRepository extends JpaRepository<Despesa, Long>{
    List<Despesa> findByUsuarioId(Long usuarioId);
    List<Despesa> findByUsuarioIdAndDataBetween(Long usuarioId, LocalDate inicio, LocalDate fim);
}
