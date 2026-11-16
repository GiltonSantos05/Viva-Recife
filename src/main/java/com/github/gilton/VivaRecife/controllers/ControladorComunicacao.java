package com.github.gilton.VivaRecife.controllers;

import com.github.gilton.VivaRecife.dto.RespostaSolicitacao;
import com.github.gilton.VivaRecife.services.ServicoComunicacao;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("comunicacoes")
public class ControladorComunicacao {

    private final ServicoComunicacao servicoComunicacao;

    public ControladorComunicacao(ServicoComunicacao servicoComunicacao) {
        this.servicoComunicacao = servicoComunicacao;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RespostaSolicitacao> criar(
            @RequestParam("titulo") String titulo,
            @RequestParam("localizacao") String localizacao,
            @RequestParam("latitude") String latitude,
            @RequestParam("longitude") String longitude,
            @RequestParam("detalhamentoProblema") String detalhamentoProblema,
            @RequestParam("tipoProblema") String tipoProblema,
            @RequestPart(value = "foto", required = false) MultipartFile foto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(servicoComunicacao.criar(titulo, localizacao, latitude, longitude, detalhamentoProblema, tipoProblema, foto));
    }
}
