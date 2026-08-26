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
        try {
            return ResponseEntity.ok(turnoService.getTurnoById(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/agenda")
    public ResponseEntity<List<TurnoResponseDTO>> getTurnosAgenda(@RequestParam Long veterinarioId, @RequestParam LocalDate fecha) {
        return ResponseEntity.ok(turnoService.getTurnosByVeterinarioAndFecha(veterinarioId, fecha));
    }

    @PostMapping
    public ResponseEntity<?> createTurno(@RequestBody TurnoRequestDTO turnoRequestDTO) {
        try {
            TurnoResponseDTO turnoResponseDTO = turnoService.createTurno(turnoRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(turnoResponseDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> updateState(@PathVariable Long id, @RequestParam EstadoTurno estado, @RequestParam(required = false) String observaciones) {
        try {
            return ResponseEntity.ok(turnoService.updateState(id, estado, observaciones));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTurno(@PathVariable Long id) {
        try {
            turnoService.deleteTurno(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
