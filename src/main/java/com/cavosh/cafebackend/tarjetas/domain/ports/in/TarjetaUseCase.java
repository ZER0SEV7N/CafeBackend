package com.cavosh.cafebackend.tarjetas.domain.ports.in;

import com.cavosh.cafebackend.tarjetas.domain.model.TarjetaUsuario;

import java.util.List;

public interface TarjetaUseCase {

    record RegisterTarjetaCommand(
            Integer usuarioId,
            String numeroTarjeta,
            String titular,
            String marca,
            boolean predeterminado
    ) {}

    TarjetaUsuario registerNewTarjeta(RegisterTarjetaCommand command);
    List<TarjetaUsuario> listTarjetas(Integer usuarioId);
    void checkDefaults(Integer usuarioId, Integer tarjetaId);
    void deleteTarjeta(Integer usuarioId, Integer tarjetaId);
}