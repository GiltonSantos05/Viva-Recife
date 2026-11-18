package com.github.gilton.VivaRecife.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ManipuladorGlobalExcecoes {

    @ExceptionHandler(ExcecaoSolicitacaoNaoEncontrada.class)
    public ResponseEntity<ErroPadrao> tratarSolicitacaoNaoEncontrada(ExcecaoSolicitacaoNaoEncontrada ex, HttpServletRequest requisicao) {
        return responder(HttpStatus.NOT_FOUND, "Recurso não encontrado", ex.getMessage(), requisicao);
    }

    @ExceptionHandler(ExcecaoUsuarioNaoEncontrado.class)
    public ResponseEntity<ErroPadrao> tratarUsuarioNaoEncontrado(ExcecaoUsuarioNaoEncontrado ex, HttpServletRequest requisicao) {
        return responder(HttpStatus.NOT_FOUND, "Recurso não encontrado", ex.getMessage(), requisicao);
    }

    @ExceptionHandler(ExcecaoUsuarioNaoAutenticado.class)
    public ResponseEntity<ErroPadrao> tratarUsuarioNaoAutenticado(ExcecaoUsuarioNaoAutenticado ex, HttpServletRequest requisicao) {
        return responder(HttpStatus.UNAUTHORIZED, "Não autenticado", ex.getMessage(), requisicao);
    }

    @ExceptionHandler({ExcecaoTokenInvalido.class, ExcecaoSenhaIncorreta.class, AuthenticationException.class})
    public ResponseEntity<ErroPadrao> tratarCredenciaisInvalidas(RuntimeException ex, HttpServletRequest requisicao) {
        String mensagem = ex.getMessage() != null && !ex.getMessage().isBlank()
                ? ex.getMessage()
                : new ExcecaoUsuarioNaoAutenticado().getMessage();
        return responder(HttpStatus.UNAUTHORIZED, "Credenciais inválidas", mensagem, requisicao);
    }

    @ExceptionHandler({ExcecaoPermissaoNegada.class, AccessDeniedException.class})
    public ResponseEntity<ErroPadrao> tratarPermissaoNegada(RuntimeException ex, HttpServletRequest requisicao) {
        String mensagem = ex.getMessage() != null && !ex.getMessage().isBlank()
                ? ex.getMessage()
                : new ExcecaoPermissaoNegada().getMessage();
        return responder(HttpStatus.FORBIDDEN, "Acesso negado", mensagem, requisicao);
    }

    @ExceptionHandler(ExcecaoEmailJaCadastrado.class)
    public ResponseEntity<ErroPadrao> tratarEmailJaCadastrado(ExcecaoEmailJaCadastrado ex, HttpServletRequest requisicao) {
        return responder(HttpStatus.CONFLICT, "Conflito", ex.getMessage(), requisicao);
    }

    @ExceptionHandler(ExcecaoSenhasDiferentes.class)
    public ResponseEntity<ErroPadrao> tratarSenhasDiferentes(ExcecaoSenhasDiferentes ex, HttpServletRequest requisicao) {
        return responder(HttpStatus.BAD_REQUEST, "Requisição inválida", ex.getMessage(), requisicao);
    }

    @ExceptionHandler({ExcecaoGeracaoToken.class, ExcecaoArmazenamentoFoto.class})
    public ResponseEntity<ErroPadrao> tratarFalhaInterna(RuntimeException ex, HttpServletRequest requisicao) {
        return responder(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor", ex.getMessage(), requisicao);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroPadrao> tratarArgumentoInvalido(IllegalArgumentException ex, HttpServletRequest requisicao) {
        return responder(HttpStatus.BAD_REQUEST, "Requisição inválida", ex.getMessage(), requisicao);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroPadrao> tratarExcecaoGenerica(Exception ex, HttpServletRequest requisicao) {
        String mensagem = ex.getMessage() != null && !ex.getMessage().isBlank()
                ? ex.getMessage()
                : "Ocorreu um erro inesperado. Tente novamente mais tarde.";
        return responder(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor", mensagem, requisicao);
    }

    private ResponseEntity<ErroPadrao> responder(HttpStatus status, String erro, String mensagem, HttpServletRequest requisicao) {
        ErroPadrao resposta = new ErroPadrao(
                LocalDateTime.now(),
                status.value(),
                erro,
                mensagem,
                requisicao.getRequestURI()
        );
        return ResponseEntity.status(status).body(resposta);
    }
}
