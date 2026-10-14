package com.eso.application;

import com.eso.domain.exception.TurnoNotFoundException;
import com.eso.domain.model.EstadoTurno;
import com.eso.domain.model.Turno;
import com.eso.domain.port.in.TurnoService;
import com.eso.domain.port.out.MascotaServicePort;
import com.eso.domain.port.out.TurnoRepositoryPort;
import com.eso.domain.port.out.VeterinarioServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TurnoServiceImpl implements TurnoService {

    private final TurnoRepositoryPort turnoRepository;
    private final MascotaServicePort mascotaService;
    private final VeterinarioServicePort veterinarioService;

    @Override
    public List<Turno> findAll() {
        return turnoRepository.findAll();
    }

    @Override
    public Turno findById(Long id) {
        Turno turno = turnoRepository.findById(id);
        if (turno == null) {
            throw new TurnoNotFoundException("Turno no encontrado");
        }
        return turno;
    }

    @Override
    public List<Turno> findByVeterinarioId(Long veterinarioId) {
        return turnoRepository.findByVeterinarioId(veterinarioId);
    }

    @Override
    public List<Turno> findByMascotaId(Long mascotaId) {
        return turnoRepository.findByMascotaId(mascotaId);
    }

    @Override
    @Transactional
    public Turno save(Turno turno) {
        if (!mascotaService.existsById(turno.getMascotaId())) {
            throw new MascotaNotFoundException(turno.getMascotaId());
        }
        if (!veterinarioService.existsById(turno.getVeterinarioId())) {
            throw new VeterinarioNotFoundException(turno.getVeterinarioId());
        }
        turno.setEstado(EstadoTurno.PENDIENTE);
        return turnoRepository.save(turno);
    }

    @Override
    @Transactional
    public Turno cancelar(Long id) {
        Turno turno = obtenerEntidad(id);
        turno.setEstado(EstadoTurno.CANCELADO);
        return turnoRepository.save(turno);
    }

    private Turno obtenerEntidad(Long id) {
        Turno turno = turnoRepository.findById(id);
        if (turno == null) {
            throw new ResourceNotFoundException("No se encontró el turno con ID: " + id, id);
        }
        return turno;
    }
}