package com.cavosh.cafebackend.tarjetas.domain.ports.out;

import com.cavosh.cafebackend.tarjetas.domain.model.TarjetaUsuario;

import java.util.List;
import java.util.Optional;

public interface TarjetaRepositoryPort {
    TarjetaUsuario save(TarjetaUsuario tarjeta);
    Optional<TarjetaUsuario> findByIdAndUsuarioId(Integer id, Integer usuarioId);
    List<TarjetaUsuario> findByUsuarioId(Integer usuarioId);
    void uncheckDefaults(Integer usuarioId);
    void deleteByIdAndUsuarioId(Integer id, Integer usuarioId);
    long countByUsuarioId(Integer usuarioId);
}