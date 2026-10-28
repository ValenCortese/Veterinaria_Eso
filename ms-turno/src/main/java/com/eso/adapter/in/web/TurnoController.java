package com.eso.adapter.in.web;

import com.eso.adapter.in.web.dto.TurnoMapper;
import com.eso.adapter.in.web.dto.TurnoRequest;
import com.eso.adapter.in.web.dto.TurnoResponse;
import com.eso.domain.model.Turno;
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
        List<TurnoResponse> turnos = turnoService.findAll().stream().map(TurnoMapper::toResponse).toList();
        return ResponseEntity.ok(turnos);
    }

    @Operation(summary = "Busca un turno por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Turno encontrado"),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TurnoResponse> getTurnoById(@PathVariable Long id) {
        Turno turno = turnoService.findById(id);
        return ResponseEntity.ok(TurnoMapper.toResponse(turno));
    }

    @Operation(summary = "Busca turnos por ID del veterinario")
    @GetMapping("/veterinario/{veterinarioId}")
    public ResponseEntity<List<TurnoResponse>> getTurnosByVeterinario(@PathVariable Long veterinarioId) {
        List<TurnoResponse> responses = turnoService.findByVeterinarioId(veterinarioId).stream()
                .map(TurnoMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Busca turnos por ID de la mascota")
    @GetMapping("/mascota/{mascotaId}")
    public ResponseEntity<List<TurnoResponse>> getTurnosByMascota(@PathVariable Long mascotaId) {
        List<TurnoResponse> responses = turnoService.findByMascotaId(mascotaId).stream()
                .map(TurnoMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Registra un nuevo turno")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Turno creado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos (ej. fecha en el pasado)"),
            @ApiResponse(responseCode = "409", description = "Superposición de turno")
    })
    @PostMapping
    public ResponseEntity<TurnoResponse> createTurno(@Valid @RequestBody TurnoRequest turnoRequest) {
        Turno turno = turnoService.save(TurnoMapper.toEntity(turnoRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(TurnoMapper.toResponse(turno));
    }

    @Operation(summary = "Cancela un turno existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Turno cancelado correctamente"),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado")
    })
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<TurnoResponse> cancelTurno(
            @Parameter(description = "ID del turno a cancelar") @PathVariable Long id) {
        Turno turno = turnoService.cancel(id);
        return ResponseEntity.ok(TurnoMapper.toResponse(turno));
    }
}