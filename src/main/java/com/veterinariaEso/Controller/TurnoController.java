package com.veterinariaEso.Controller;

import com.veterinariaEso.DTO.TurnoRequestDTO;
import com.veterinariaEso.DTO.TurnoResponseDTO;
import com.veterinariaEso.Exception.ResourceNotFoundException;
import com.veterinariaEso.Model.EstadoTurno;
import com.veterinariaEso.Service.TurnoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

@Tag(name="Turnos", description = "CRUD - Veterinaria Eso")
@RestController
@RequestMapping("/api/turnos")
@RequiredArgsConstructor
public class TurnoController {

    @Autowired
    private final TurnoService turnoService;

    @Operation(summary = "Lista todos los turnos",
            description = "Devuelve una lista conteniendo todos los turnos y su información."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de turnos obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<TurnoResponseDTO>> getAllTurnos() {
        return ResponseEntity.ok(turnoService.getAllTurnos());
    }

    @Operation(summary = "Busca un turno por ID",
            description = "Devuelve toda la información registrada al ID del turno buscado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Turno encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró un turno con el ID indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getTurnoById(
            @Parameter(description = "ID del turno", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(turnoService.getTurnoById(id));
    }

    @Operation(summary = "Lista los turnos de un veterinario en una fecha",
            description = "Devuelve toda la información de los turnos de un veterinario con el ID insertado en la fecha indicada"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agenda obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Los parámetros enviados no son válidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/agenda")
    public ResponseEntity<List<TurnoResponseDTO>> getTurnosAgenda(
            @Parameter(description = "ID del veterinario", example = "1")
            @RequestParam Long veterinarioId,
            @Parameter(description = "Fecha de la agenda en formato yyyy-MM-dd", example = "2026-10-15")
            @RequestParam LocalDate fecha) {
        return ResponseEntity.ok(turnoService.getTurnosByVeterinarioAndFecha(veterinarioId, fecha));
    }

    @Operation(summary = "Registra un nuevo turno",
            description = "Crea un nuevo turno. La fecha tiene que ser hoy o en el futuro y la hora debe ser entre 0 y 23"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Turno creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos"),
            @ApiResponse(responseCode = "404", description = "No se encontró la mascota o el veterinario indicado"),
            @ApiResponse(responseCode = "409", description = "Ya existe un turno para el veterinario en ese horario"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> createTurno(@Valid @RequestBody TurnoRequestDTO turnoRequestDTO) {
        TurnoResponseDTO turnoResponseDTO = turnoService.createTurno(turnoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(turnoResponseDTO);
    }

    @Operation(summary = "Actualizar un turno",
            description = "Actualiza un turno. La fecha tiene que ser hoy o en el futuro y la hora debe ser entre 0 y 23"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Turno actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Los parámetros enviados no son válidos"),
            @ApiResponse(responseCode = "404", description = "No se encontró un turno con el ID indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> updateState(
            @Parameter(description = "ID del turno a actualizar", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Nuevo estado del turno", example = "PENDIENTE")
            @RequestParam EstadoTurno estado,
            @Parameter(description = "Observaciones opcionales del turno", example = "El paciente debe asistir con ayuno")
            @RequestParam(required = false) String observaciones) {
        return ResponseEntity.ok(turnoService.updateState(id, estado, observaciones));
    }

    @Operation(summary = "Borrar un turno según su ID",
            description = "Borra el turno del ID ingresado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Turno eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró un turno con el ID indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTurno(
            @Parameter(description = "ID del turno a eliminar", example = "1")
            @PathVariable Long id) {
        turnoService.deleteTurno(id);
        return ResponseEntity.noContent().build();
    }
}
