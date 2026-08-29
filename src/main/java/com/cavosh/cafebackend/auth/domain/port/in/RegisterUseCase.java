package com.cavosh.cafebackend.auth.domain.port.in;

import com.cavosh.cafebackend.auth.domain.model.Usuario;

public interface RegisterUseCase {

    Usuario registrar(RegistrarCommand command);

    /**
     * DTO para registrar
     * @param fullName
     * @param email
     * @param password
     * @param confirmPassword
     */
    record RegistrarCommand(
            String fullName,
            String email,
            String password,
            String confirmPassword
    ) {}
}
