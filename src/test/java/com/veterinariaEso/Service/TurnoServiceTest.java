package com.veterinariaEso.Service;

import com.veterinariaEso.DTO.TurnoRequestDTO;
import com.veterinariaEso.Exception.TurnoSuperpuestoException;
import com.veterinariaEso.Mapper.TurnoMapper;
import com.veterinariaEso.Model.Mascota;
import com.veterinariaEso.Model.Turno;
import com.veterinariaEso.Model.Veterinario;
import com.veterinariaEso.Repository.MascotaRepository;
import com.veterinariaEso.Repository.TurnoRepository;
import com.veterinariaEso.Repository.VeterinarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TurnoServiceTest {

    @Mock
    TurnoRepository turnoRepository;
    @InjectMocks
    TurnoService turnoService;
    @Mock
    TurnoMapper turnoMapper;
    @Mock
    MascotaRepository mascotaRepository;
    @Mock
    VeterinarioRepository veterinarioRepository;

    // Test 1: createTurno exitoso - save llamado una vez
    @Test
    void createTurno_exitoso() {
        // DADO
        TurnoRequestDTO turnoRequestDTO = new TurnoRequestDTO();
        LocalDate fecha = LocalDate.of(2026, 10, 15);
        LocalTime hora = LocalTime.of(14, 30);
        turnoRequestDTO.setVeterinarioId(1L);
        turnoRequestDTO.setMascotaId(1L);
        turnoRequestDTO.setFecha(fecha);
        turnoRequestDTO.setHora(hora);
        when(mascotaRepository.findById(1L)).thenReturn(Optional.of(new Mascota()));
        when(veterinarioRepository.findById(1L)).thenReturn(Optional.of(new Veterinario()));
        when(turnoRepository.existsByVeterinarioIdAndFechaAndHora(eq(1L), eq(fecha), eq(hora))).thenReturn(false);
        // CUANDO
        turnoService.createTurno(turnoRequestDTO);
        // ENTONCES
        verify(turnoRepository, times(1)).save(any(Turno.class));
    }

    // Test 2: createTurno con superposición
    @Test
    void createTurno_conSuperposicion_lanzaExcepcionYNoLlamaSave() {
        // DADO
        TurnoRequestDTO turnoRequestDTO = new TurnoRequestDTO();
        LocalDate fecha = LocalDate.of(2026, 10, 15);
        LocalTime hora = LocalTime.of(14, 30);
        turnoRequestDTO.setVeterinarioId(1L);
        turnoRequestDTO.setMascotaId(1L);
        turnoRequestDTO.setFecha(fecha);
        turnoRequestDTO.setHora(hora);
        when(mascotaRepository.findById(1L)).thenReturn(Optional.of(new Mascota()));
        when(veterinarioRepository.findById(1L)).thenReturn(Optional.of(new Veterinario()));
        when(turnoRepository.existsByVeterinarioIdAndFechaAndHora(eq(1L), eq(fecha), eq(hora))).thenReturn(true);
        // CUANDO y ENTONCES
        assertThrows(TurnoSuperpuestoException.class, () -> {turnoService.createTurno(turnoRequestDTO);});
        verify(turnoRepository, never()).save(any(Turno.class));
    }
}
