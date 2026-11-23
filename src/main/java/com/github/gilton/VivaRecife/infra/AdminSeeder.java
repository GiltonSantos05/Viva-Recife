package com.github.gilton.VivaRecife.infra;

import com.github.gilton.VivaRecife.entity.Admin;
import com.github.gilton.VivaRecife.entity.enums.Role;
import com.github.gilton.VivaRecife.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSeeder implements CommandLineRunner {

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        if (personRepository.existsByEmail(adminEmail)) return;
        Admin admin = new Admin();
        admin.setNome("GiltonAdministrador");
        admin.setEmail(adminEmail);
        admin.setSenhaUsuario(adminPassword);
        admin.setRole(Role.ADMIN);

        admin.setSenhaUsuario(passwordEncoder.encode(adminPassword));

        personRepository.save(admin);
        System.out.println("(LOG) ADMIN CRIADA");
    }
}
