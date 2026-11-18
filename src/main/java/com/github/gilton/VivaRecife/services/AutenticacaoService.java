package com.github.gilton.VivaRecife.services;

import com.github.gilton.VivaRecife.dto.CurrentUserDTO;
import com.github.gilton.VivaRecife.dto.LoginResponseDTO;
import com.github.gilton.VivaRecife.dto.RegistrarRequestDTO;
import com.github.gilton.VivaRecife.dto.RegistrarResponseDTO;
import com.github.gilton.VivaRecife.dto.request.LoginRequestDTO;
import com.github.gilton.VivaRecife.entity.Person;
import com.github.gilton.VivaRecife.entity.Usuario;
import com.github.gilton.VivaRecife.entity.enums.Role;
import com.github.gilton.VivaRecife.exceptions.ExcecaoEmailJaCadastrado;
import com.github.gilton.VivaRecife.exceptions.ExcecaoSenhaIncorreta;
import com.github.gilton.VivaRecife.exceptions.ExcecaoSenhasDiferentes;
import com.github.gilton.VivaRecife.exceptions.ExcecaoUsuarioNaoAutenticado;
import com.github.gilton.VivaRecife.exceptions.ExcecaoUsuarioNaoEncontrado;
import com.github.gilton.VivaRecife.repositories.PersonRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class AutenticacaoService {

    private final PersonRepository personRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public AutenticacaoService(PersonRepository personRepository, TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public CurrentUserDTO getCurrentUserData() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ExcecaoUsuarioNaoAutenticado();
        }
        String email = authentication.getName();

        Person person = personRepository.findByEmail(email)
                .orElseThrow(() -> new ExcecaoUsuarioNaoEncontrado(email));

        String role = person.getRole() != null
                ? person.getRole().name()
                : Role.USUARIO.name();

        return new CurrentUserDTO(
                person.getId(),
                person.getNome(),
                person.getEmail(),
                role
        );
    }

    public LoginResponseDTO login (LoginRequestDTO login) {
        Person person = this.personRepository.findByEmail(login.email()).orElseThrow(
                () -> new ExcecaoUsuarioNaoEncontrado(login.email())
        );

        if (!passwordEncoder.matches(login.senhaUsuario(), person.getSenhaUsuario())) {
            throw new ExcecaoSenhaIncorreta();
        }

        String token = this.tokenService.generateToken(person);
        return new LoginResponseDTO(person.getNome(), token);
    }

    public RegistrarResponseDTO registrar(RegistrarRequestDTO registro) {

        if (this.personRepository.existsByEmail(registro.email())) {
            throw new ExcecaoEmailJaCadastrado(registro.email());
        }

        if (!Objects.equals(registro.senhaUsuario(), registro.senhaUsuarioConfirmacao())) {
            throw new ExcecaoSenhasDiferentes();
        }
        Person person = new Usuario();

        person.setNome(registro.nome());
        person.setEmail(registro.email());
        person.setSenhaUsuario(passwordEncoder.encode(registro.senhaUsuario()));

        person.setRole(Role.USUARIO);

        person.setCriadoEm(LocalDateTime.now());
        personRepository.save(person);


        return new RegistrarResponseDTO(person.getNome(), person.getEmail());
    }

}
