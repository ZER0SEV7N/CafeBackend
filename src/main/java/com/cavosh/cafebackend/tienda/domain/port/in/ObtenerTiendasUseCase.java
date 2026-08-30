package com.cavosh.cafebackend.tienda.domain.port.in;

import com.cavosh.cafebackend.tienda.domain.model.Cafeteria;

import java.util.List;

public interface ObtenerTiendasUseCase {
    List<Cafeteria> getAllActiveTiendas();
    List<Cafeteria> getTiendasByCiudad(String ciudad);
    List<Cafeteria> getFrequentlyChosenTiendas();
}
