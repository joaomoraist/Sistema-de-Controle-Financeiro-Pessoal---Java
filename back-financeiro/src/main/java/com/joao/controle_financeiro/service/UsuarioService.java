package com.joao.controle_financeiro.service;

import com.joao.controle_financeiro.dto.UsuarioRequestDTO;
import com.joao.controle_financeiro.model.Usuario;
import com.joao.controle_financeiro.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    public Usuario register(UsuarioRequestDTO dto) {

        // Verifica se o email já existe
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        // Converte DTO -> Entidade
        Usuario usuario = modelMapper.map(dto, Usuario.class);

        // Criptografa a senha
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));

        // Salva no banco
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(Long id, UsuarioRequestDTO dto) {

        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Se email mudou, validar duplicidade
        if (!existente.getEmail().equals(dto.getEmail()) &&
                usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email já está em uso");
        }

        existente.setNome(dto.getNome());
        existente.setEmail(dto.getEmail());

        // Se o cara enviou senha nova → criptografa e salva
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            existente.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        return usuarioRepository.save(existente);
    }

    public void deletar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}
