package com.eso.adapter.in.web;

import com.eso.adapter.in.web.dto.TurnoRequest;
import com.eso.adapter.in.web.dto.TurnoResponse;
import com.eso.domain.port.in.TurnoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Turnos", description = "CRUD - Veterinaria - Turnos")
@RestController
@RequestMapping("/api/turnos")
@RequiredArgsConstructor
public class TurnoController {

    private final TurnoService turnoService;

    @Operation(summary = "Lista todos los turnos")
    @GetMapping
    public ResponseEntity<List<TurnoResponse>> getAllTurnos() {
        return ResponseEntity.ok(turnoService.findAll());
    }

    @Operation(summary = "Busca un turno por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Turno encontrado"),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TurnoResponse> getTurnoById(@PathVariable Long id) {
        return ResponseEntity.ok(turnoService.findById(id));
    }

    @Operation(summary = "Busca turnos por ID del veterinario")
    @GetMapping("/veterinario/{veterinarioId}")
    public ResponseEntity<List<TurnoResponse>> getTurnosByVeterinario(@PathVariable Long veterinarioId) {
        return ResponseEntity.ok(turnoService.findByVeterinarioId(veterinarioId));
    }

    @Operation(summary = "Busca turnos por ID de la mascota")
    @GetMapping("/mascota/{mascotaId}")
    public ResponseEntity<List<TurnoResponse>> getTurnosByMascota(@PathVariable Long mascotaId) {
        return ResponseEntity.ok(turnoService.findByMascotaId(mascotaId));
    }

    @Operation(summary = "Registra un nuevo turno")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Turno creado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos (ej. fecha en el pasado)"),
            @ApiResponse(responseCode = "409", description = "Superposición de turno")
    })
    @PostMapping
    public ResponseEntity<TurnoResponse> createTurno(@Valid @RequestBody TurnoRequest turnoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(turnoService.save(turnoRequest));
    }

    @Operation(summary = "Cancela un turno existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Turno cancelado correctamente"),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado")
    })
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<TurnoResponse> cancelarTurno(
            @Parameter(description = "ID del turno a cancelar") @PathVariable Long id) {
        return ResponseEntity.ok(turnoService.cancelar(id));
    }
}