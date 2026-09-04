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

    /**
     * Metodo para mandar el correo de recuperacion
     * Se requerie que tengan configurado el correo y la contraseña en el application.properties
     * @param destinatario - Email del usuario a enviar la recuperacion
     * @param nombreUsuario - Nombre del usuario que solicita recuperacion
     * @param token - Token del enlace
     */
    public void sendCorreoRecuperacion(String destinatario, String nombreUsuario, String token) {
        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setTo(destinatario);
            mensaje.setSubject("Cavosh Cafe - Restablecer Contraseña");
            mensaje.setText(String.format(
                    """
                            Hola %s,
                            
                            Has solicitado restablecer tu contraseña en Cavosh Cafe.
                            Utiliza el siguiente token en la app para crear una nueva clave:
                            
                            %s
                            
                            Este token expirará en 15 minutos.
                            Si no solicitaste este cambio, puedes ignorar este mensaje.""",
                    nombreUsuario, token
            ));

            mailSender.send(mensaje);
            log.info("Correo de recuperación enviado a {}", destinatario);
        } catch (Exception e) {
            log.error("Fallo al enviar correo a {}: {}", destinatario, e.getMessage());
        }
    }
}