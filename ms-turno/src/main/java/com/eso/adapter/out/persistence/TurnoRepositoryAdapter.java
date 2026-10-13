package com.eso.adapter.out.persistence;

import com.eso.domain.model.Turno;
import com.eso.domain.port.out.TurnoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TurnoRepositoryAdapter implements TurnoRepositoryPort {

    private final TurnoJpaRepository jpaRepository;

    @Override
    public List<Turno> findAll() { return jpaRepository.findAll(); }

    @Override
    public Turno findById(Long id) { return jpaRepository.findById(id).orElse(null); }

    @Override
    public List<Turno> findByVeterinarioId(Long veterinarioId) { return jpaRepository.findByVeterinarioId(veterinarioId); }

    @Override
    public List<Turno> findByMascotaId(Long mascotaId) { return jpaRepository.findByMascotaId(mascotaId); }

    @Override
    public Turno save(Turno turno) { return jpaRepository.save(turno); }

    @Override
    public boolean existsByVeterinarioIdAndFechaHora(Long veterinarioId, LocalDateTime fechaHora) {
        return jpaRepository.existsByVeterinarioIdAndFechaHora(veterinarioId, fechaHora);
    }
}