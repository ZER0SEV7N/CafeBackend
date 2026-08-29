package com.cavosh.cafebackend.auth.domain.model;

import java.time.LocalDateTime;

public record Usuario(
    Integer id,
    String fullName,
    String email,
    String password,
    Rol rol,
    AuthProveedor proveedor,
    Integer ptnLealtad,
    boolean activo,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public static Usuario nuevoCliente (String fullName, String email, String hashPassword) {
        LocalDateTime hoy = LocalDateTime.now();
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
