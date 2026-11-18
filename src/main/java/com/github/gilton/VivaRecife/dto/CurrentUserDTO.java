package com.github.gilton.VivaRecife.dto;

import java.util.UUID;

public record CurrentUserDTO(UUID id,
                             String nome,
                             String email,
                             String role) {
}
