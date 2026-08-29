package com.cavosh.cafebackend.global.infrastructure.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

/**
 * ResponseGlobal: Clase que representa la estructura de respuesta global para las respuestas HTTP en la aplicación.
 * Esta clase puede ser utilizada para estandarizar las respuestas enviadas desde los controladores,
 * @param success
 * @param status
 * @param message
 * @param data
 * @param timestamp
 * @param <T>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseGlobal<T>(
    boolean success,
    int status,
    String message,
    T data,
    Instant timestamp
) {
    //Metodos estaticas de Ayuda (Factory Methods) para crear instancias de ResponseGlobal con diferentes combinaciones de campos.
    public static <T> ResponseGlobal<T> success(int status, T data, String message) {
        return new ResponseGlobal<>(true, status, message, data, Instant.now());
    }

    // Por defecto asume 200 OK
    public static <T> ResponseGlobal<T> success(T data, String message) {
        return new ResponseGlobal<>(true, 200, message, data, Instant.now());
    }

    public static <T> ResponseGlobal<T> success(T data) {
        return new ResponseGlobal<>(true, 200, "Operación exitosa", data, Instant.now());
    }

    public static <T> ResponseGlobal<T> error(int status, String message) {
        return new ResponseGlobal<>(false, status, message, null, Instant.now());
    }

}
