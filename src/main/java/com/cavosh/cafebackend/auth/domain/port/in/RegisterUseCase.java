package com.cavosh.cafebackend.auth.domain.port.in;

import com.cavosh.cafebackend.auth.domain.model.Usuario;

/**
 * Interfaz del caso de uso de registrar localmente
 * Permite registrar un usuario en la base de datos
 * Utiliza un record registrarCommand para recibir los datos de entrada (fullName, email, password, confirmPassword)
 * Devuelve un objeto Usuario
 */
public interface RegisterUseCase {

    record RegisterCommand(String fullName,  String email,  String password, String confirmPassword) {}

    Usuario register(RegisterCommand command);
}
