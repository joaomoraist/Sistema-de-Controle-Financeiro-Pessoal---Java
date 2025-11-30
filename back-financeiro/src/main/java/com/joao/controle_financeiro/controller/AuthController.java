package com.joao.controle_financeiro.controller;

import com.joao.controle_financeiro.dto.*;
import com.joao.controle_financeiro.model.Usuario;
import com.joao.controle_financeiro.repository.UsuarioRepository;
import com.joao.controle_financeiro.security.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody com.joao.controle_financeiro.dto.UsuarioRequestDTO dto) {
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("E-mail já cadastrado");
        }

        // mapeia DTO
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        // codifica senha
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        Usuario saved = usuarioRepository.save(usuario);

        // gera token imediato
        String token = jwtUtil.generateToken(saved.getEmail());

        AuthResponseDTO response = new AuthResponseDTO(token, saved.getId(), saved.getEmail(), saved.getNome());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getSenha())
            );

            String token = jwtUtil.generateToken(loginDTO.getEmail());

            Usuario usuario = usuarioRepository.findByEmail(loginDTO.getEmail()).get();
            AuthResponseDTO response = new AuthResponseDTO(token, usuario.getId(), usuario.getEmail(), usuario.getNome());

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }
}
