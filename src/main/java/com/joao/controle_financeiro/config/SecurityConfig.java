package com.joao.controle_financeiro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // desativa proteção CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/api-docs/**",
                                "/usuarios/**" // libera o cadastro
                        ).permitAll() // permite acesso sem login
                        .anyRequest().authenticated() // exige login pro resto
                )
                .formLogin(login -> login.disable()) // desativa a tela padrão de login
                .httpBasic(basic -> basic.disable()); // desativa login básico

        return http.build();
    }
}
