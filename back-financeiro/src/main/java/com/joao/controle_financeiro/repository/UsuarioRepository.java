package com.joao.controle_financeiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.joao.controle_financeiro.model.Usuario;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
