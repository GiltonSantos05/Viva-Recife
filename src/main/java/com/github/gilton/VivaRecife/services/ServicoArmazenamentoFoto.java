package com.github.gilton.VivaRecife.services;

import com.github.gilton.VivaRecife.exceptions.ExcecaoArmazenamentoFoto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ServicoArmazenamentoFoto {

    private static final String DIRETORIO_FOTOS = "B.Os fotos";

    public String salvarFoto(MultipartFile foto) {
        if (foto == null || foto.isEmpty()) {
            return null;
        }

        try {
            Path diretorio = Paths.get(DIRETORIO_FOTOS);
            Files.createDirectories(diretorio);

            String nomeOriginal = foto.getOriginalFilename();
            String extensao = "";
            if (nomeOriginal != null && nomeOriginal.contains(".")) {
                extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
            }

            String nomeArquivo = UUID.randomUUID() + extensao;
            Path destino = diretorio.resolve(nomeArquivo);
            Files.copy(foto.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

            return diretorio.resolve(nomeArquivo).toString();
        } catch (IOException e) {
            throw new ExcecaoArmazenamentoFoto(e);
        }
    }
}
