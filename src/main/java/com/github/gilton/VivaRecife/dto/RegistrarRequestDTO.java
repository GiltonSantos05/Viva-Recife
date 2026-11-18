package com.github.gilton.VivaRecife.dto;

public record RegistrarRequestDTO(
        String nome,
        String email,
        String senhaUsuario,
        String senhaUsuarioConfirmacao
) {
}
