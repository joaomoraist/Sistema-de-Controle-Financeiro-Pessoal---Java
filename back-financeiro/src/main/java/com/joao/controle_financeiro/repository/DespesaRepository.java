package com.joao.controle_financeiro.repository;

import com.joao.controle_financeiro.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;



public interface DespesaRepository extends JpaRepository<Despesa, Long>{
    List<Despesa> findByUsuarioId(Long usuarioId);
    List<Despesa> findByUsuarioIdAndDataBetween(Long usuarioId, LocalDate inicio, LocalDate fim);

    @Query("""
        SELECT 
            EXTRACT(YEAR FROM d.data) AS ano,
            EXTRACT(MONTH FROM d.data) AS mes,
            SUM(d.valor) AS total
        FROM Despesa d
        GROUP BY ano, mes
        ORDER BY ano, mes
    """)
    List<Object[]> totalPorMes();
}

