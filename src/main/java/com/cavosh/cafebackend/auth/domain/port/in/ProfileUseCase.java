package com.cavosh.cafebackend.auth.domain.port.in;

import com.cavosh.cafebackend.auth.domain.model.Usuario;

public interface ProfileUseCase {

    record UpdateProfileCommand(Integer usuarioId, String fullName) {}

    Usuario getProfile(Integer usuarioId);
    Usuario updateMyProfile(UpdateProfileCommand command);
}
