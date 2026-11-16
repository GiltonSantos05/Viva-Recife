package com.github.gilton.VivaRecife.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ManipuladorGlobalExcecoes {

    @ExceptionHandler(ExcecaoSolicitacaoNaoEncontrada.class)
    public ResponseEntity<ErroPadrao> tratarSolicitacaoNaoEncontrada(ExcecaoSolicitacaoNaoEncontrada ex, HttpServletRequest requisicao) {
        ErroPadrao erro = new ErroPadrao(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Recurso não encontrado",
                ex.getMessage(),
                requisicao.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroPadrao> tratarExcecaoGenerica(Exception ex, HttpServletRequest requisicao) {
        ErroPadrao erro = new ErroPadrao(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno do servidor",
                "Ocorreu um erro inesperado. Tente novamente mais tarde.",
                requisicao.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }

    public record ErroPadrao(
            LocalDateTime momento,
            int status,
            String erro,
            String mensagem,
            String caminho
    ) {
    }
}
