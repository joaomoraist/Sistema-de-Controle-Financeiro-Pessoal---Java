package com.joao.controle_financeiro.controller;

import com.joao.controle_financeiro.dto.UsuarioRequestDTO;
import com.joao.controle_financeiro.dto.UsuarioResponseDTO;
import com.joao.controle_financeiro.model.Usuario;
import com.joao.controle_financeiro.service.UsuarioService;
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
    private UsuarioService usuarioService;

    @Autowired
    private ModelMapper modelMapper;

    // Listar todos
    @GetMapping
    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioService.listarTodos()
                .stream()
                .map(u -> modelMapper.map(u, UsuarioResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Cadastrar novo usuário
    @PostMapping
    public ResponseEntity<?> cadastrarUsuario(@Valid @RequestBody UsuarioRequestDTO dto) {

        Usuario salvo = usuarioService.register(dto);

        UsuarioResponseDTO response = modelMapper.map(salvo, UsuarioResponseDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Atualizar usuário
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO dto
    ) {

        Usuario atualizado = usuarioService.atualizar(id, dto);

        UsuarioResponseDTO response = modelMapper.map(atualizado, UsuarioResponseDTO.class);

        return ResponseEntity.ok(response);
    }

    // Deletar usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable Long id) {

        usuarioService.deletar(id);

        return ResponseEntity.ok("Usuário removido com sucesso!");
    }
}
