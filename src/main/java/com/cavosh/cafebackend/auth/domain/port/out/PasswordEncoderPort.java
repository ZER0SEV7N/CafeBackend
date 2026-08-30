package com.cavosh.cafebackend.auth.domain.port.out;

/**
 * Interfaz del puerto para codificar y verificar contraseñas
 * utiliza CharSequence para recibir la contraseña en texto plano y String para la contraseña codificada
 */
public interface PasswordEncoderPort {
    String encode(CharSequence rawPassword);
    boolean matches(CharSequence rawPassword, String encodePassword);
}
