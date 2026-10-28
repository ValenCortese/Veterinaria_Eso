package com.eso.domain.port.in;

import com.eso.domain.model.Turno;

import java.util.List;

public interface TurnoService {
    List<Turno> findAll();
    Turno findById(Long id);
    List<Turno> findByVeterinarioId(Long veterinarioId);
    List<Turno> findByMascotaId(Long mascotaId);
    Turno save(Turno turno);
    Turno cancel(Long id);
}