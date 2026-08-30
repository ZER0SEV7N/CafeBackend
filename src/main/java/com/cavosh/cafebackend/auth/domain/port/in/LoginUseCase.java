package com.cavosh.cafebackend.auth.domain.port.in;

import com.cavosh.cafebackend.auth.domain.model.Usuario;

/**
 *
 */
public interface LoginUseCase {
    record LoginCommand(
            String email,
            String password
    ) {}

    /**
     *
     * @param token
     * @param usuario
     */
    record LoginResult(
        String token,
        Usuario usuario
    ) {}

    /**
     *
     * @param command
     * @return
     */
    LoginResult login(LoginCommand command);

}
