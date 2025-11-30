package com.joao.controle_financeiro.service;

import com.joao.controle_financeiro.dto.UsuarioRequestDTO;
import com.joao.controle_financeiro.model.Usuario;
import com.joao.controle_financeiro.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // LISTAR
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    // CADASTRAR
    public Usuario register(UsuarioRequestDTO dto) {

        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado");
        }

        Usuario usuario = modelMapper.map(dto, Usuario.class);
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));

        return usuarioRepository.save(usuario);
    }

    // ATUALIZAR
    public Usuario atualizar(Long id, UsuarioRequestDTO dto) {

        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!existente.getEmail().equals(dto.getEmail()) &&
                usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("E-mail já está em uso");
        }

        existente.setNome(dto.getNome());
        existente.setEmail(dto.getEmail());
        existente.setSenha(passwordEncoder.encode(dto.getSenha()));

        return usuarioRepository.save(existente);
    }

    // DELETAR
    public void deletar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado");
        }

        usuarioRepository.deleteById(id);
    }
}
