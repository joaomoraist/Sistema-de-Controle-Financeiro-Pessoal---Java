package com.joao.controle_financeiro.dto;

public class AuthResponseDTO {
    private String token;
    private Long userId;
    private String email;
    private String nome;

    public AuthResponseDTO() {}

    public AuthResponseDTO(String token, Long userId, String email, String nome) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.nome = nome;
    }

    // getters e setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
