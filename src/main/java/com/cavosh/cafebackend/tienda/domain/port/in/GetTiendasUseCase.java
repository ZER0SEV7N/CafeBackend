package com.cavosh.cafebackend.tienda.domain.port.in;

import com.cavosh.cafebackend.tienda.domain.model.Cafeteria;

import java.util.List;

/** 
 * Interfaz que define los casos de uso para obtener información sobre las tiendas (cafeterías) en la aplicación.
 * Proporciona métodos para obtener todas las tiendas activas, buscar tiendas por ciudad y obtener las tiendas más frecuentemente elegidas por los usuarios.
 */
public interface GetTiendasUseCase {
    List<Cafeteria> getAllActiveTiendas();
    List<Cafeteria> getTiendasByCiudad(String ciudad);
    List<Cafeteria> getFrequentlyChosenTiendas();
}
