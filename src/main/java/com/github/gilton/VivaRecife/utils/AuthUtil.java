package com.github.gilton.VivaRecife.utils;


import com.github.gilton.VivaRecife.entity.Person;
import com.github.gilton.VivaRecife.exceptions.ExcecaoUsuarioNaoAutenticado;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    public Person getUserLoggedIn() {
        Authentication autenticacao = SecurityContextHolder.getContext().getAuthentication();
        if (autenticacao == null || !autenticacao.isAuthenticated()
                || !(autenticacao.getPrincipal() instanceof Person usuario)) {
            throw new ExcecaoUsuarioNaoAutenticado();
        }
        return usuario;
    }
}