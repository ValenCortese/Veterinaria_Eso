package com.eso.domain.port.out;

import com.eso.domain.model.Mascota;
import java.util.List;

public interface MascotaRepositoryPort {
    List<Mascota> findAll();
    Mascota findById(Long id);
    Mascota save(Mascota mascota);
    void deleteById(Long id);

    List<Mascota> findByDuenioId(Long duenioId);
    boolean existsByNombreAndDuenioId(String nombre, Long duenioId);
}