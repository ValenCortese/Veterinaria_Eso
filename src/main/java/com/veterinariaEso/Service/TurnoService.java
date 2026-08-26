package com.veterinariaEso.Service;

import com.veterinariaEso.DTO.TurnoRequestDTO;
import com.veterinariaEso.DTO.TurnoResponseDTO;
import com.veterinariaEso.Exception.ResourceNotFoundException;
import com.veterinariaEso.Mapper.TurnoMapper;
import com.veterinariaEso.Model.EstadoTurno;
import com.veterinariaEso.Model.Mascota;
import com.veterinariaEso.Model.Turno;
import com.veterinariaEso.Model.Veterinario;
import com.veterinariaEso.Repository.MascotaRepository;
import com.veterinariaEso.Repository.TurnoRepository;
import com.veterinariaEso.Repository.VeterinarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TurnoService {

    private final TurnoRepository turnoRepository;
    private final VeterinarioRepository veterinarioRepository;
    private final MascotaRepository mascotaRepository;
    private final TurnoMapper turnoMapper;

    public List<TurnoResponseDTO> getAllTurnos() {
        return turnoRepository.findAll().stream().map(turnoMapper::toTurnoResponseDTO).collect(Collectors.toList());
    }

    public TurnoResponseDTO getTurnoById(Long id) {
        return turnoMapper.toTurnoResponseDTO(turnoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turno", id)));
    }

    public List<TurnoResponseDTO> getTurnosByVeterinarioAndFecha(Long veterinarioId, LocalDate fecha) {
        return turnoRepository.findByVeterinarioIdAndFecha(veterinarioId, fecha).stream().map(turnoMapper::toTurnoResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TurnoResponseDTO createTurno(TurnoRequestDTO turnoRequestDTO) {
        Mascota mascota = mascotaRepository.findById(turnoRequestDTO.getMascotaId())
                .orElseThrow(() -> new ResourceNotFoundException("Mascota", turnoRequestDTO.getMascotaId()));

        Veterinario veterinario = veterinarioRepository.findById(turnoRequestDTO.getVeterinarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Vterinario", turnoRequestDTO.getVeterinarioId()));
        // ver que no tiene otro turno el veterinario en ese dia y hora
        if (turnoRepository.existsByVeterinarioIdAndFechaAndHora(turnoRequestDTO.getVeterinarioId(), turnoRequestDTO.getFecha(), turnoRequestDTO.getHora())) {
            throw new RuntimeException("Ya existe un turno del veterinario en ese horario");
        }

        Turno turno = new Turno();
        turno.setFecha(turnoRequestDTO.getFecha());
        turno.setHora(turnoRequestDTO.getHora());
        turno.setMotivo(turnoRequestDTO.getMotivo());
        turno.setMascota(mascota);
        turno.setVeterinario(veterinario);
        turno.setEstado(EstadoTurno.PENDIENTE);
        // mas cosas
        return turnoMapper.toTurnoResponseDTO(turnoRepository.save(turno));
    }

    @Transactional
    public TurnoResponseDTO updateState(Long id, EstadoTurno nuevoEstado, String observaciones) {
        Turno turno = turnoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Turno", id));
        turno.setEstado(nuevoEstado);
        if (observaciones != null) {
            turno.setObservaciones(observaciones);
        }
        return turnoMapper.toTurnoResponseDTO(turnoRepository.save(turno));
    }

    public void deleteTurno(Long id) {
        if (!turnoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Turno", id);
        }
        turnoRepository.deleteById(id);
    }
}
