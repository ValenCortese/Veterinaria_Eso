package com.eso.domain.port.out;

import com.eso.domain.model.Turno;
import java.time.LocalDateTime;
import java.util.List;

public interface TurnoRepositoryPort {
    List<Turno> findAll();
    Turno findById(Long id);
    List<Turno> findByVeterinarioId(Long veterinarioId);
    List<Turno> findByMascotaId(Long mascotaId);
    Turno save(Turno turno);
    boolean existsByVeterinarioIdAndFechaHora(Long veterinarioId, LocalDateTime fechaHora);
}