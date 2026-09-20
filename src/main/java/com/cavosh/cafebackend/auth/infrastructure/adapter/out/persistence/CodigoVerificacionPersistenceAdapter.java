package com.cavosh.cafebackend.auth.infrastructure.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.cavosh.cafebackend.auth.domain.port.out.CodigoVerificacionRepositoryPort;
import com.cavosh.cafebackend.auth.infrastructure.adapter.out.persistence.repository.CodigoVerificacionRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CodigoVerificacionPersistenceAdapter implements CodigoVerificacionRepositoryPort {

    private final CodigoVerificacionRepository codigoVerificacionRepository;

    public void registrarNuevoCodigo(String email, String codigo) {
        codigoVerificacionRepository.generarCodigoOtp(email, codigo);
    }

    public ResultadoVerificacion verificarCodigo(String email, String codigo) {
        List<Object[]> resultado = codigoVerificacionRepository.verificarCodigoOtp(email, codigo);
        
        if(resultado.isEmpty())
            return new ResultadoVerificacion(1, "Error consultando el procedimiento de verificacion", null);

        //Extraer los valores de la primera fila del resultado
        Object[] fila = resultado.get(0);
        int codigoResultado = ((Number) fila[0]).intValue();
        String mensaje = (String) fila[1];
        Integer usuarioId = fila[2] != null ? ((Number) fila[2]).intValue() : null;

        //Retornar un objeto ResultadoVerificacion con los valores extraídos
        return new ResultadoVerificacion(codigoResultado, mensaje, usuarioId);
    }
}
