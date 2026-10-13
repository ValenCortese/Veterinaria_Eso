package com.eso.application;

import com.eso.adapter.in.web.dto.TurnoMapper;
import com.eso.adapter.in.web.dto.TurnoRequest;
import com.eso.adapter.in.web.dto.TurnoResponse;
import com.eso.domain.exception.ResourceNotFoundException;
import com.eso.domain.exception.TurnoSuperpuestoException;
import com.eso.domain.model.EstadoTurno;
import com.eso.domain.model.Turno;
import com.eso.domain.port.in.TurnoService;
import com.eso.domain.port.out.TurnoRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TurnoServiceImpl implements TurnoService {

    private final TurnoRepositoryPort turnoRepository;

    @Override
    public List<TurnoResponse> findAll() {
        return turnoRepository.findAll().stream().map(TurnoMapper::toResponse).toList();
    }

    @Override
    public TurnoResponse findById(Long id) {
        return TurnoMapper.toResponse(obtenerEntidad(id));
    }

    @Override
    public List<TurnoResponse> findByVeterinarioId(Long veterinarioId) {
        return turnoRepository.findByVeterinarioId(veterinarioId).stream().map(TurnoMapper::toResponse).toList();
    }

    @Override
    public List<TurnoResponse> findByMascotaId(Long mascotaId) {
        return turnoRepository.findByMascotaId(mascotaId).stream().map(TurnoMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public TurnoResponse save(TurnoRequest request) {
        if (turnoRepository.existsByVeterinarioIdAndFechaHora(request.getVeterinarioId(), request.getFechaHora())) {
            throw new TurnoSuperpuestoException("El veterinario ya tiene un turno asignado en esa fecha y hora.");
        }

        Turno turnoGuardado = turnoRepository.save(TurnoMapper.toEntity(request));
        return TurnoMapper.toResponse(turnoGuardado);
    }

    @Override
    @Transactional
    public TurnoResponse cancelar(Long id) {
        Turno turno = obtenerEntidad(id);
        turno.setEstado(EstadoTurno.CANCELADO);
        return TurnoMapper.toResponse(turnoRepository.save(turno));
    }

    private Turno obtenerEntidad(Long id) {
        Turno turno = turnoRepository.findById(id);
        if (turno == null) {
            throw new ResourceNotFoundException("No se encontró el turno con ID: " + id, id);
        }
        return turno;
    }
}