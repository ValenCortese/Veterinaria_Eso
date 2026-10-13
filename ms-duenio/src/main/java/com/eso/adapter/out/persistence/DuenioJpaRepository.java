package com.eso.adapter.out.persistence;

import com.eso.domain.model.Duenio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DuenioJpaRepository extends JpaRepository<Duenio, Long> {
    Optional<Duenio> findByNombre(String nombre);
    Optional<Duenio> findByEmail(String email);
    Optional<Duenio> findByNombreAndApellido(String nombre, String apellido);
    boolean existsByEmail(String email);
    boolean existsByNombre(String nombre);
}