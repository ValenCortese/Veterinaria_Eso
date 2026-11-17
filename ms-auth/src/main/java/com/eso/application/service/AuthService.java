package com.eso.application.service;

import com.eso.adapter.in.web.dto.AuthResponse;
import com.eso.adapter.in.web.dto.LoginRequest;
import com.eso.adapter.in.web.dto.RegisterRequest;
import com.eso.adapter.out.persistence.UsuarioJpaRepository;
import com.eso.domain.exception.CredencialesInvalidasException;
import com.eso.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioJpaRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest request) {
        Usuario usuario = new Usuario();
        usuario.setUsername(request.username());
        usuario.setPassword(passwordEncoder.encode(request.password()));
        usuario.setRol(request.rol());

        usuarioRepository.save(usuario);

        String token = jwtService.generateToken(usuario.getUsername(), usuario.getRol());
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByUsername(request.username())
                .orElseThrow(() -> new CredencialesInvalidasException("Usuario o contraseña incorrectos"));

        if (!passwordEncoder.matches(request.password(), usuario.getPassword())) {
            throw new CredencialesInvalidasException("Usuario o contraseña incorrectos");
        }

        String token = jwtService.generateToken(usuario.getUsername(), usuario.getRol());
        return new AuthResponse(token);
    }
}