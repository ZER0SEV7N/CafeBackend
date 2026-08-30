package com.cavosh.cafebackend.auth.application.usecases;

import com.cavosh.cafebackend.auth.domain.model.Usuario;
import com.cavosh.cafebackend.auth.domain.port.in.LoginUseCase;
import com.cavosh.cafebackend.auth.domain.port.out.PasswordEncoderPort;
import com.cavosh.cafebackend.auth.domain.port.out.TokenProviderPort;
import com.cavosh.cafebackend.auth.domain.port.out.UsuarioRepositoryPort;
import com.cavosh.cafebackend.global.domain.exception.BusinessRuleException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final TokenProviderPort tokenProvider;

    @Transactional(readOnly = true)
    public LoginResult login(LoginCommand command) {
        String email = command.email().toLowerCase().trim();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Correo o contraseña incorrectas"));

        if(!usuario.activo())
            throw new BusinessRuleException("La cuenta de usuario se encuentra desactivada");

        if(!passwordEncoder.matches(command.password(), usuario.password()))
            throw new BadCredentialsException("Credenciales invalidas");

        String token = tokenProvider.generateToken(usuario);
        return new LoginResult(token, usuario);
    }
}
