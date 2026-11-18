package com.github.gilton.VivaRecife.repositories;

import com.github.gilton.VivaRecife.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {

    Optional<Person> findByEmail(String email);

    boolean existsByEmail(String email);
}
