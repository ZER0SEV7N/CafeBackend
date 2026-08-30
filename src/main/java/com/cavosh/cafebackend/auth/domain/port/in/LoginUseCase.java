package com.cavosh.cafebackend.auth.domain.port.in;

import com.cavosh.cafebackend.auth.domain.model.Usuario;

/**
 * Interfaz de caso de uso
 * Permite autenticar a un usuario
 * Utiliza un record loginCommand para recibir los datos de entrada (email y password)
 * Utiliza un record loginResult para devolver los datos de salida (token y usuario)
 */
public interface LoginUseCase {
    record LoginCommand(String email, String password) {}
    record LoginResult(String token, Usuario usuario) {}
    LoginResult login(LoginCommand command);
}
