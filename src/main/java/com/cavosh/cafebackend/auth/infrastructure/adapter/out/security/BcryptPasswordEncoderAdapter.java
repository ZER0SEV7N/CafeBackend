package com.cavosh.cafebackend.auth.infrastructure.adapter.out.security;

import com.cavosh.cafebackend.auth.domain.port.out.PasswordEncoderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BcryptPasswordEncoderAdapter implements PasswordEncoderPort {

    private final PasswordEncoderPort passwordEncoder;

    //Funcion para hashear la contraseña
    public String encode(CharSequence rawPassword) {
        return  passwordEncoder.encode(rawPassword);
    }

    //Funcion para verificar ambas contraseñas
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
