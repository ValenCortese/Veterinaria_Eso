package com.eso.domain.port.out;

import com.eso.domain.model.Duenio;

import java.util.List;
import java.util.Optional;

public interface DuenioRepositoryPort {
    Duenio findByNombre(String nombre);

    Duenio findByEmail(String email);

    Optional<Duenio> findByNombreAndApellido(String nombre, String apellido);

    boolean existsByEmail(String email);

    boolean existsByNombre(String nombre);

    List<Duenio> findAll();

    Duenio save(Duenio duenio);

    Duenio findById(Long id);

    void deleteById(Long id);
}