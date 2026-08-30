package com.cavosh.cafebackend.tienda.application.usecases;

import com.cavosh.cafebackend.tienda.domain.model.Cafeteria;
import com.cavosh.cafebackend.tienda.domain.port.in.ObtenerTiendasUseCase;
import com.cavosh.cafebackend.tienda.domain.port.out.TiendaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementacion de los metodos del caso de uso para obtener tiendas
 */
@Service
@RequiredArgsConstructor
public class ObtenerTiendasUseCaseImpl implements ObtenerTiendasUseCase {

    private final TiendaRepositoryPort tiendaRepository;

    /**
     * Obtiene todas las tiendas activas.
     * @return Lista de cafeterias activas.
     */
    @Transactional(readOnly = true)
    public List<Cafeteria> getAllActiveTiendas() {
        return tiendaRepository.findAllActive();
    }

    /**
     * Obtener la tienda por ciudad
     * @param ciudad - Ciudad a buscar
     * @return las tiendas por las ciudad
     */
    @Transactional(readOnly = true)
    public List<Cafeteria> getTiendasByCiudad(String ciudad){
        if(ciudad == null || ciudad.trim().isBlank())
            return tiendaRepository.findAllActive();

        return tiendaRepository.findByCiudad(ciudad.trim());
    }

    /**
     * Obtiene las tiendas mas elegidas.
     * @return Lista de cafeterias mas elegidas.
     */
    @Transactional(readOnly = true)
    public List<Cafeteria> getFrequentlyChosenTiendas() {
        return tiendaRepository.findFrequentlyChosen();
    }
}
