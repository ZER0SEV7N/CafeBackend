package com.cavosh.cafebackend.auth.infrastructure.adapter.in.web;
import com.cavosh.cafebackend.auth.domain.model.Usuario;
import com.cavosh.cafebackend.auth.domain.port.in.ProfileUseCase;
import com.cavosh.cafebackend.auth.infrastructure.adapter.in.web.dto.UpdateProfileRequest;
import com.cavosh.cafebackend.auth.infrastructure.adapter.in.web.dto.UserProfileResponse;
import com.cavosh.cafebackend.global.infrastructure.web.response.ResponseGlobal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final ProfileUseCase profileUseCase;

    @GetMapping("/profile")
    public ResponseEntity<ResponseGlobal<UserProfileResponse>> getProfile(@AuthenticationPrincipal Usuario usuarioAuth) {
        Usuario usuario = profileUseCase.getProfile(usuarioAuth.id());
        return ResponseEntity.ok(ResponseGlobal.success(UserProfileResponse.from(usuario), "Perfil obtenido"));
    }

    @PutMapping("/profile")
    public ResponseEntity<ResponseGlobal<UserProfileResponse>> updateProfile(
            @AuthenticationPrincipal Usuario usuarioAuth,
            @Valid @RequestBody UpdateProfileRequest request
    ) {
        var command = new ProfileUseCase.UpdateProfileCommand(usuarioAuth.id(), request.fullName());
        Usuario actualizado = profileUseCase.updateMyProfile(command);
        return ResponseEntity.ok(ResponseGlobal.success(UserProfileResponse.from(actualizado), "Perfil actualizado"));
    }
}