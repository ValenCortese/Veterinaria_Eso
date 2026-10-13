package com.eso.adapter.in.web;

import com.eso.adapter.in.web.dto.VeterinarioRequest;
import com.eso.adapter.in.web.dto.VeterinarioResponse;
import com.eso.domain.port.in.VeterinarioService;
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

@Tag(name="Veterinarios", description = "CRUD - Veterinaria - Veterinarios")
@RestController
@RequestMapping("/api/veterinarios")
@RequiredArgsConstructor
public class VeterinarioController {

    private final VeterinarioService veterinarioService;

    @Operation(summary = "Lista todos los veterinarios",
            description = "Devuelve una lista conteniendo todos los veterinarios y su información."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de veterinarios obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<VeterinarioResponse>> getAllVeterinarios() {
        return ResponseEntity.ok(veterinarioService.findAll());
    }

    @Operation(summary = "Busca un veterinario por ID",
            description = "Devuelve toda la información registrada al ID del veterinario buscado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Veterinario encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró un veterinario con el ID indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VeterinarioResponse> getVeterinarioById(
            @Parameter(description = "ID del veterinario", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(veterinarioService.findById(id));
    }

    @Operation(summary = "Busca veterinarios por especialidad",
            description = "Devuelve una lista de todos los veterinarios que pertenecen a la especialidad indicada."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Veterinarios encontrados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/especialidad/{especialidad}")
    public ResponseEntity<List<VeterinarioResponse>> getVeterinariosByEspecialidad(
            @Parameter(description = "Especialidad del veterinario", example = "Cirugía")
            @PathVariable String especialidad) {
        return ResponseEntity.ok(veterinarioService.findByEspecialidad(especialidad));
    }

    @Operation(summary = "Registra un nuevo veterinario",
            description = "Crea un nuevo veterinario. Todos los valores deben estar completos, con formato válido y la matrícula/email no deben existir previamente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Veterinario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos (ej. formato de matrícula incorrecto)"),
            @ApiResponse(responseCode = "409", description = "La matrícula o el email ya se encuentran registrados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<VeterinarioResponse> createVeterinario(@Valid @RequestBody VeterinarioRequest veterinarioRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(veterinarioService.save(veterinarioRequest));
    }

    @Operation(summary = "Actualizar un veterinario según su ID",
            description = "Actualiza la información del veterinario del ID ingresado. Se aplican las mismas validaciones que en la creación."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Veterinario actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos"),
            @ApiResponse(responseCode = "404", description = "No se encontró un veterinario con el ID indicado"),
            @ApiResponse(responseCode = "409", description = "La matrícula o el email chocan con otro registro existente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<VeterinarioResponse> updateVeterinario(
            @Parameter(description = "ID del veterinario a actualizar", example = "1")
            @PathVariable Long id, @Valid @RequestBody VeterinarioRequest veterinarioRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(veterinarioService.update(id, veterinarioRequest));
    }

    @Operation(summary = "Borrar un veterinario según su ID",
            description = "Borra el veterinario del ID ingresado del sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Veterinario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró un veterinario con el ID indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVeterinario(
            @Parameter(description = "ID del veterinario a eliminar", example = "1")
            @PathVariable Long id) {
        veterinarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}