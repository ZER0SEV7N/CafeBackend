package com.cavosh.cafebackend.auth.infrastructure.adapter.out.mail;
import com.cavosh.cafebackend.auth.domain.port.out.EmailSenderPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * EmailSenderAdapter: Adapter de envío de correos que implementa la interfaz EmailSenderPort.
 * Utiliza JavaMailSender para enviar correos electrónicos. 
 */
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

    public void sendVerificationCodeEmail(String toEmail, String code) {
        String subject = "Código de verificación - Cavosh Café";
        String content = """
            <div style="font-family: Arial, sans-serif; max-width: 500px; margin: auto; padding: 20px; border: 1px solid #e0e0e0; border-radius: 8px;">
                <h2 style="color: #2c3e50; text-align: center;">¡Bienvenido a Cavosh Café! ☕</h2>
                <p style="font-size: 14px; color: #555;">Ingresa el siguiente código de 4 dígitos en la aplicación para verificar tu cuenta:</p>
                <div style="text-align: center; margin: 30px 0;">
                    <span style="font-size: 32px; font-weight: bold; letter-spacing: 10px; color: #d35400; background-color: #fbeee6; padding: 12px 24px; border-radius: 8px;">%s</span>
                </div>
                <p style="font-size: 12px; color: #888; text-align: center;">Este código caducará en 15 minutos. Si no solicitaste esta cuenta, puedes ignorar este mensaje.</p>
            </div>
            """.formatted(code);

        try {
            jakarta.mail.internet.MimeMessage message = mailSender.createMimeMessage();
            org.springframework.mail.javamail.MimeMessageHelper helper = new org.springframework.mail.javamail.MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Error enviando el correo con el código de verificación", e);
        }
    }
}