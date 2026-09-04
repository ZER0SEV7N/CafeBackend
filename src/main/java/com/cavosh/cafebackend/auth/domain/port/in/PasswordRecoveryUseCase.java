package com.cavosh.cafebackend.auth.domain.port.in;

public interface PasswordRecoveryUseCase {
    record SolicitarRecuperacionCommand(String email) {}
    record RestablecerPasswordCommand(String token, String nuevaPassword, String confirmarPassword) {}

    void solicitarRecuperacion(SolicitarRecuperacionCommand command);
    void restablecerPassword(RestablecerPasswordCommand command);
}