package com.eso.domain.port.out;

import com.eso.domain.model.Veterinario;
import java.util.List;

public interface VeterinarioRepositoryPort {
    List<Veterinario> findAll();
    Veterinario findById(Long id);
    List<Veterinario> findByEspecialidad(String especialidad);
    Veterinario save(Veterinario veterinario);
    void deleteById(Long id);
    boolean existsByEmail(String email);
    boolean existsByMatricula(String matricula);
}