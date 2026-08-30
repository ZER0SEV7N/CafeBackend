package com.cavosh.cafebackend.auth.domain.model;

import java.time.Instant;

/**
 * Modelo Usuario - Entidad:
 * @param id - Id del usuario
 * @param fullName - Nombre completo
 * @param email - Correo
 * @param password - Contraseña
 * @param rol - Rol
 * @param proveedor - Proveedor de inicio de sesion
 * @param puntosRecompensa - puntos de recompensa (se adquieren comprando cafe)
 * @param activo - Activo o Inactivo
 * @param createdAt - Fecha de creacion
 * @param updatedAt - Fecha de actualizacion
 */
public record Usuario(
    Integer id,
    String fullName,
    String email,
    String password,
    Rol rol,
    AuthProveedor proveedor,
    Integer puntosRecompensa,
    boolean activo,
    Instant createdAt,
    Instant updatedAt
) {

    /**
     * Modelo estatico para crear un nuevo cliente LOCAL
     * @param fullName - Nombre Completo
     * @param email - Correo
     * @param hashPassword - Contraseña hasheada
     * @return - retorna el usuario
     */
    public static Usuario nuevoCliente (String fullName, String email, String hashPassword) {
        Instant hoy = Instant.now();
        return new Usuario(
                null,
                fullName,
                email,
                hashPassword,
                Rol.CLIENTE,
                AuthProveedor.LOCAL,
                0,
                true,
                hoy,
                hoy
        );


    }
}
