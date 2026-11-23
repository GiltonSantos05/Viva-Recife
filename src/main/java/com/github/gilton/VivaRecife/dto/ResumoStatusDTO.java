package com.github.gilton.VivaRecife.dto;

public record ResumoStatusDTO(
        long total,
        long recebidas,
        long emAnalise,
        long encaminhadas,
        long canceladas,
        long resolvidas
) {
}
