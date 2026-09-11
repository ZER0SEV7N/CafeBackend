package com.cavosh.cafebackend.tienda.domain.port.out;


import com.cavosh.cafebackend.tienda.domain.model.Cafeteria;

import java.util.List;
import java.util.Optional;

/** 
 * Interfaz que define los métodos de acceso a datos para las tiendas (cafeterías) en la aplicación.
 * Proporciona métodos para buscar una cafetería por su ID, obtener todas las cafeterías activas, 
 * buscar cafeterías por ciudad y obtener las cafeterías más frecuentemente elegidas por los usuarios.
 */
public interface TiendaRepositoryPort {
    Optional<Cafeteria> findById(Integer id);
    List<Cafeteria> findAllActive();
    List<Cafeteria> findByCiudad(String city);
    List<Cafeteria> findFrecuentesPorUsuario(Integer usuarioId);
}
