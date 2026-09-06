package com.cavosh.cafebackend.cupones.infrastructure.adapter.in.web;

import com.cavosh.cafebackend.cupones.domain.ports.in.ValidateCuponUseCase;
import com.cavosh.cafebackend.cupones.infrastructure.adapter.in.web.doc.CuponDoc;
import com.cavosh.cafebackend.cupones.infrastructure.adapter.in.web.dto.CuponValidadoResponse;
import com.cavosh.cafebackend.cupones.infrastructure.adapter.in.web.dto.ValidarCuponRequest;
import com.cavosh.cafebackend.global.infrastructure.web.response.ResponseGlobal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cupones")
@RequiredArgsConstructor
public class CuponController implements CuponDoc {

    private final ValidateCuponUseCase validarCuponUseCase;

    @Override
    @PostMapping("/validar")
    public ResponseEntity<ResponseGlobal<CuponValidadoResponse>> validarCupon(@Valid @RequestBody ValidarCuponRequest request) {
        var resultado = validarCuponUseCase.validateAndAplicate(request.codigo(), request.subtotal());
        return ResponseEntity.ok(ResponseGlobal.success(CuponValidadoResponse.from(resultado), "Cupón verificado"));
    }
}