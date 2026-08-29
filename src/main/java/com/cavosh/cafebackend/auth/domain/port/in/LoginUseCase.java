package com.cavosh.cafebackend.auth.domain.port.in;

public interface LoginUseCase {

    AuthResult login(LoginCommand command);

    record LoginCommand(
            String email,
            String password
    ) {}

    record AuthResult(
            String accessToken,
            String tokenType,
            Integer userId,
            String fullName,
            String email,
            String rol,
            Integer ptnLealtad
    ){}
}
