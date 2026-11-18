package com.github.gilton.VivaRecife.infra;

import com.github.gilton.VivaRecife.entity.Person;
import com.github.gilton.VivaRecife.exceptions.ExcecaoPermissaoNegada;
import com.github.gilton.VivaRecife.exceptions.ExcecaoUsuarioNaoAutenticado;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class VerificadorAcesso {

    public void verificarAcessoUsuario() {
        Authentication autenticacao = SecurityContextHolder.getContext().getAuthentication();

        if (autenticacao == null
                || !autenticacao.isAuthenticated()
                || !(autenticacao.getPrincipal() instanceof Person)) {
            throw new ExcecaoUsuarioNaoAutenticado();
        }

        boolean possuiPerfilUsuario = autenticacao.getAuthorities().stream()
                .anyMatch(autoridade -> "ROLE_USUARIO".equals(autoridade.getAuthority()));

        if (!possuiPerfilUsuario) {
            throw new ExcecaoPermissaoNegada();
        }
    }
}
