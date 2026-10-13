package com.eso.adapter.out.persistence;

import com.eso.domain.model.Veterinario;
import com.eso.domain.port.out.VeterinarioRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class VeterinarioRepositoryAdapter implements VeterinarioRepositoryPort {

    private final VeterinarioJpaRepository jpaRepository;

    @Override
    public List<Veterinario> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Veterinario findById(Long id) {
        return jpaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Veterinario> findByEspecialidad(String especialidad) {
        return jpaRepository.findByEspecialidad(especialidad);
    }

    @Override
    public Veterinario save(Veterinario veterinario) {
        return jpaRepository.save(veterinario);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByMatricula(String matricula) {
        return jpaRepository.existsByMatricula(matricula);
    }
}