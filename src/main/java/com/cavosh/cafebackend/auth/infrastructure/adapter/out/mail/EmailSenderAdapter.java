package com.cavosh.cafebackend.auth.infrastructure.adapter.out.mail;
import com.cavosh.cafebackend.auth.domain.port.out.EmailSenderPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailSenderAdapter implements EmailSenderPort {

    private final JavaMailSender mailSender;

    public void sendCorreoRecuperacion(String destinatario, String nombreUsuario, String token) {
        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setTo(destinatario);
            mensaje.setSubject("Cavosh Cafe - Restablecer Contraseña");
            mensaje.setText(String.format(
                    "Hola %s,\n\nHas solicitado restablecer tu contraseña en Cavosh Cafe.\n" +
                            "Utiliza el siguiente token en la app para crear una nueva clave:\n\n" +
                            "%s\n\n" +
                            "Este token expirará en 15 minutos.\n" +
                            "Si no solicitaste este cambio, puedes ignorar este mensaje.",
                    nombreUsuario, token
            ));

            mailSender.send(mensaje);
            log.info("Correo de recuperación enviado a {}", destinatario);
        } catch (Exception e) {
            log.error("Fallo al enviar correo a {}: {}", destinatario, e.getMessage());
        }
    }
}