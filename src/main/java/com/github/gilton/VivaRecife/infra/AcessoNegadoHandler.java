package com.github.gilton.VivaRecife.infra;

import tools.jackson.databind.ObjectMapper;
import com.github.gilton.VivaRecife.exceptions.ErroPadrao;
import com.github.gilton.VivaRecife.exceptions.ExcecaoPermissaoNegada;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class AcessoNegadoHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public AcessoNegadoHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest requisicao, HttpServletResponse resposta,
                       AccessDeniedException excecao) throws IOException {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ErroPadrao erro = new ErroPadrao(
                LocalDateTime.now(),
                status.value(),
                "Acesso negado",
                new ExcecaoPermissaoNegada().getMessage(),
                requisicao.getRequestURI()
        );

        resposta.setStatus(status.value());
        resposta.setContentType(MediaType.APPLICATION_JSON_VALUE);
        resposta.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(resposta.getWriter(), erro);
    }
}
