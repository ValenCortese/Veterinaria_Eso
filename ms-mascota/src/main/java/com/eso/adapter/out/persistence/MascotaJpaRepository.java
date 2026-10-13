package com.eso.adapter.out.persistence;

import com.eso.domain.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MascotaJpaRepository extends JpaRepository<Mascota, Long> {

    Optional<Mascota> findByNombre(String nombre);
    List<Mascota> findByDuenioId(Long duenioId);
    boolean existsByNombreAndDuenioId(String nombre, Long duenioId);
}