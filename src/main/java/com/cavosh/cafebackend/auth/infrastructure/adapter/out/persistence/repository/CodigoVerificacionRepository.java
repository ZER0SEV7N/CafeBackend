package com.cavosh.cafebackend.auth.infrastructure.adapter.out.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cavosh.cafebackend.auth.infrastructure.adapter.out.persistence.entity.CodigoVerificacionEntity;

public interface CodigoVerificacionRepository extends JpaRepository<CodigoVerificacionEntity, Integer>{
    
    @Query(value = "SELECT sp_generar_codigo_otp(:email, :codigo, 15)", nativeQuery = true)
    Integer generarCodigoOtp(@Param("email") String email, @Param("codigo") String codigo);

    @Query(value = "SELECT resultado_codigo, mensaje, usuario_id FROM sp_verificar_codigo_otp(:email, :codigo)", nativeQuery = true)
    List<Object[]> verificarCodigoOtp(@Param("email") String email, @Param("codigo") String codigo);
}
