package com.eso.adapter.out.persistence;

import com.eso.domain.model.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VeterinarioJpaRepository extends JpaRepository<Veterinario, Long> {
    List<Veterinario> findByEspecialidad(String especialidad);
    boolean existsByEmail(String email);
    boolean existsByMatricula(String matricula);
}