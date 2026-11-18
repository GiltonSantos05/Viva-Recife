package com.github.gilton.VivaRecife.controllers;

import com.github.gilton.VivaRecife.dto.LoginResponseDTO;
import com.github.gilton.VivaRecife.dto.RegistrarRequestDTO;
import com.github.gilton.VivaRecife.dto.RegistrarResponseDTO;
import com.github.gilton.VivaRecife.dto.request.LoginRequestDTO;
import com.github.gilton.VivaRecife.services.AutenticacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    public AutenticacaoController(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    private final AutenticacaoService autenticacaoService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO login) {
        LoginResponseDTO response = autenticacaoService.login(login);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrarResponseDTO> register(@RequestBody RegistrarRequestDTO registro) {
        RegistrarResponseDTO response = autenticacaoService.registrar(registro);
        return ResponseEntity.created(null).body(response);
    }
}
