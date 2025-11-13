package com.joao.controle_financeiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.joao.controle_financeiro.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
