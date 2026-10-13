package com.eso.adapter.out.persistance;

import com.eso.domain.model.Duenio;
import com.eso.domain.port.out.DuenioRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository // <--- Esta anotación es obligatoria para solucionar el error
@RequiredArgsConstructor
public class DuenioRepositoryAdapter implements DuenioRepositoryPort {

    // Aquí inyectarás tu interfaz de Spring Data JPA (ej. DuenioJpaRepository)
    // private final DuenioJpaRepository jpaRepository;

    @Override
    public Duenio findByNombre(String nombre) {
        // Implementación delegando a jpaRepository
        return null;
    }

    @Override
    public Duenio findByEmail(String email) {
        return null;
    }

    @Override
    public Optional<Duenio> findByNombreAndApellido(String nombre, String apellido) {
        return Optional.empty();
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return false;
    }

    @Override
    public List<Duenio> findAll() {
        return List.of();
    }

    @Override
    public Duenio save(Duenio duenio) {
        return null;
    }

    @Override
    public Duenio findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        // Implementación
    }
}