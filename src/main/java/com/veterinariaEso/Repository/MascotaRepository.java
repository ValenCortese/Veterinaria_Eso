package com.veterinariaEso.Repository;

import com.veterinariaEso.Model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {

    Optional<Mascota> findByNombre(String nombre);
    Optional<Mascota> findByNombreIgnoreCase(String nombre);
    Optional<Mascota> findByRazaIgnoreCase(String raza);
    List<Mascota> findByDuenioId(Long duenioId);
    boolean existsByNombreAndDuenio_Id(String nombre, Long duenioId);
    long countByEspecie(String especie);
}
