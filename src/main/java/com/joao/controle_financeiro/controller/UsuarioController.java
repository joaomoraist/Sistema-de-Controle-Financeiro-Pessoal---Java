package com.joao.controle_financeiro.controller;

import com.joao.controle_financeiro.dto.UsuarioRequestDTO;
import com.joao.controle_financeiro.dto.UsuarioResponseDTO;
import com.joao.controle_financeiro.model.Usuario;
import com.joao.controle_financeiro.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper; // injetado do config

    // Listar todos (retorna lista de UsuarioResponseDTO)
    @GetMapping
    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(u -> modelMapper.map(u, UsuarioResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Cadastrar novo usuário
    @PostMapping
    public ResponseEntity<?> cadastrarUsuario(@Valid @RequestBody UsuarioRequestDTO dto) {
        // verifica email duplicado
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email já cadastrado");
        }

        // converte DTO -> entidade
        Usuario entidade = modelMapper.map(dto, Usuario.class);

        // salvar
        Usuario salvo = usuarioRepository.save(entidade);

        // converter entidade salva -> response DTO
        UsuarioResponseDTO response = modelMapper.map(salvo, UsuarioResponseDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //Atualizar usuário
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO dto
    ) {
        // verificar se existe
        Usuario existente = usuarioRepository.findById(id)
                .orElse(null);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuário não encontrado");
        }
        // verificar email duplicado (se alterado)
        if (!existente.getEmail().equals(dto.getEmail())) {
            if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Este e-mail já está em uso");
            }
        }
        // atualizar campos
        existente.setNome(dto.getNome());
        existente.setEmail(dto.getEmail());
        existente.setSenha(dto.getSenha());

        Usuario atualizado = usuarioRepository.save(existente);

        UsuarioResponseDTO response = modelMapper.map(atualizado, UsuarioResponseDTO.class);

        return ResponseEntity.ok(response);
    }

    //Deletar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable Long id){
        if (!usuarioRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.ok("Usuário removido com sucesso!");
    }
}