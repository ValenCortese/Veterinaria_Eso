package com.veterinariaEso.Controller;

import com.veterinariaEso.DTO.TurnoRequestDTO;
import com.veterinariaEso.DTO.TurnoResponseDTO;
import com.veterinariaEso.Exception.ResourceNotFoundException;
import com.veterinariaEso.Model.EstadoTurno;
import com.veterinariaEso.Service.TurnoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/turnos")
@RequiredArgsConstructor
public class TurnoController {

    @Autowired
    private final TurnoService turnoService;

    @GetMapping
    public ResponseEntity<List<TurnoResponseDTO>> getAllTurnos() {
        return ResponseEntity.ok(turnoService.getAllTurnos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTurnoById(@PathVariable Long id) {
        return ResponseEntity.ok(turnoService.getTurnoById(id));
    }

    @GetMapping("/agenda")
    public ResponseEntity<List<TurnoResponseDTO>> getTurnosAgenda(@RequestParam Long veterinarioId, @RequestParam LocalDate fecha) {
        return ResponseEntity.ok(turnoService.getTurnosByVeterinarioAndFecha(veterinarioId, fecha));
    }

    @PostMapping
    public ResponseEntity<?> createTurno(@Valid @RequestBody TurnoRequestDTO turnoRequestDTO) {
        TurnoResponseDTO turnoResponseDTO = turnoService.createTurno(turnoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(turnoResponseDTO);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> updateState(@PathVariable Long id, @RequestParam EstadoTurno estado, @RequestParam(required = false) String observaciones) {
        return ResponseEntity.ok(turnoService.updateState(id, estado, observaciones));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTurno(@PathVariable Long id) {
        turnoService.deleteTurno(id);
        return ResponseEntity.noContent().build();
    }
}
