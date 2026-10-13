package com.eso.adapter.out.persistence;

import com.eso.domain.model.Duenio;
import com.eso.domain.port.out.DuenioRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DuenioRepositoryAdapter implements DuenioRepositoryPort {

    private final DuenioJpaRepository jpaRepository;

    @Override
    public Duenio findByNombre(String nombre) {
        return jpaRepository.findByNombre(nombre).orElse(null);
    }

    @Override
    public Duenio findByEmail(String email) {
        return jpaRepository.findByEmail(email).orElse(null);
    }

    @Override
    public Optional<Duenio> findByNombreAndApellido(String nombre, String apellido) {
        return jpaRepository.findByNombreAndApellido(nombre, apellido);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return jpaRepository.existsByNombre(nombre);
    }

    @Override
    public List<Duenio> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Duenio save(Duenio duenio) {
        return jpaRepository.save(duenio);
    }

    @Override
    public Duenio findById(Long id) {
        return jpaRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}