package com.github.gilton.VivaRecife.exceptions;

import java.time.LocalDateTime;

public record ErroPadrao(
        LocalDateTime momento,
        int status,
        String erro,
        String mensagem,
        String caminho
) {
}
