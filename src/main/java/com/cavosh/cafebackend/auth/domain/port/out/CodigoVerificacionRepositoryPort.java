package com.cavosh.cafebackend.auth.domain.port.out;

public interface CodigoVerificacionRepositoryPort {
    
    void registrarNuevoCodigo(String email, String codigo);

    ResultadoVerificacion verificarCodigo(String email, String codigo);

    record ResultadoVerificacion(
            int codigoResultado, //0: OK, 1: No encontrado, 2: Expirado, 3: Intentos superados, 4: Incorrecto
            String mensaje,
            Integer usuarioId
    ) {}
}
