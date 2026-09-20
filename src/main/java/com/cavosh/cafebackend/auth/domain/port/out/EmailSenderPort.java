package com.cavosh.cafebackend.auth.domain.port.out;

public interface EmailSenderPort {
    void sendCorreoRecuperacion(String destinatario, String nombreUsuario, String token);
    void sendVerificationCodeEmail(String toEmail, String code);
}
