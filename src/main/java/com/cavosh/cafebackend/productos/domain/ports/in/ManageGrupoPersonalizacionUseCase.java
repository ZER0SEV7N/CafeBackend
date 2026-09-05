package com.cavosh.cafebackend.productos.domain.ports.in;

import com.cavosh.cafebackend.productos.domain.model.GrupoPersonalizacion;

import java.math.BigDecimal;
import java.util.List;
public interface ManageGrupoPersonalizacionUseCase {
    record OpcionCommand(String nombre, BigDecimal recargoPrecio, boolean porDefecto) {}
    record CrearGrupoCommand(String nombreGrupo, boolean seleccionMultiple, boolean obligatorio, List<OpcionCommand> opciones) {}

    List<GrupoPersonalizacion> getAllGrupos();
    GrupoPersonalizacion getGrupoById(Integer id);
    GrupoPersonalizacion saveGrupo(CrearGrupoCommand command);
}
