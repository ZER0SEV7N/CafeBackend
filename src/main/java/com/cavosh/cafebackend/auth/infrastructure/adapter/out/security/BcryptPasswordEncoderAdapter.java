package com.cavosh.cafebackend.auth.infrastructure.adapter.out.security;

import com.cavosh.cafebackend.auth.domain.port.out.PasswordEncoderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Componente que implementa la interfaz PasswordEncoderPort para codificar y verificar contraseñas utilizando Bcrypt.
 * Utiliza un objeto PasswordEncoderPort para realizar las operaciones de codificación y verificación.
 */
@Component
@RequiredArgsConstructor
public class BcryptPasswordEncoderAdapter implements PasswordEncoderPort {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();;

    /**
     * Metodo para codificar una contraseña.
     * @param rawPassword - Contraseña sin codificar
     * @return Contraseña codificada
     */
    public String encode(CharSequence rawPassword) {
        return  passwordEncoder.encode(rawPassword);
    }

    /**
     * Metodo para verificar si una contraseña sin codificar coincide con una contraseña codificada.
     * @param rawPassword - Contraseña sin codificar
     * @param encodedPassword - Contraseña codificada
     * @return true si las contraseñas coinciden, false en caso contrario
     */
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
