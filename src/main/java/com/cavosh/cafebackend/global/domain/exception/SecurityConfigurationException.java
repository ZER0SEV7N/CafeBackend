package com.cavosh.cafebackend.global.domain.exception;


/**
 * Excepción personalizada para errores de configuración de seguridad.
 * Esta excepción se lanza cuando ocurre un error al construir la cadena de filtros de seguridad
 */
public class SecurityConfigurationException extends RuntimeException {
    
    public SecurityConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
