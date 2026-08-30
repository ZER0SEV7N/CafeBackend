package com.cavosh.cafebackend.tienda.domain.port.out;


import com.cavosh.cafebackend.tienda.domain.model.Cafeteria;

import java.util.List;
import java.util.Optional;

public interface TiendaRepositoryPort {
    Optional<Cafeteria> findById(Integer id);
    List<Cafeteria> findAllActive();
    List<Cafeteria> findByCiudad(String city);
    List<Cafeteria> findFrequentlyChosen();
}
