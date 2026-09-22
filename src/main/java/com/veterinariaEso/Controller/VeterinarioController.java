package com.veterinariaEso.Controller;

import com.veterinariaEso.DTO.VeterinarioDTO;
import com.veterinariaEso.Exception.ResourceNotFoundException;
import com.veterinariaEso.Service.VeterinarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;

@Tag(name="Veterinarios", description = "CRUD - Veterinaria Eso")
@RestController
@RequestMapping("/api/veterinarios")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VeterinarioController {

    @Autowired
    private VeterinarioService veterinarioService;

    @Operation(summary = "Lista todos los veterinarios",
            description = "Devuelve una lista conteniendo todos los veterinarios y su información."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de veterinarios obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<VeterinarioDTO>> getAllVeterinarios() {
        return ResponseEntity.ok(veterinarioService.getAllVeterinarios());
    }

    @Operation(summary = "Busca un veterinario por ID",
            description = "Devuelve toda la información registrada al ID del veterinario buscado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Veterinario encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró un veterinario con el ID indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getVeterinarioById(
            @Parameter(description = "ID del veterinario", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(veterinarioService.getVeterinarioById(id));
    }

    @Operation(summary = "Registra un nuevo veterinario",
            description = "Crea un nuevo veterinario. Todos los valores deben estar completos y la matricula debe tener el formato: MA-1234"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Veterinario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos"),
            @ApiResponse(responseCode = "409", description = "La matrícula ya se encuentra registrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> createVeterinario(@Valid @RequestBody VeterinarioDTO veterinarioDTO) {
        VeterinarioDTO nuevo = veterinarioService.createVeterinario(veterinarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @Operation(summary = "Actualizar un veterinario según su ID",
            description = "Actualiza el veterinario del ID ingresado. Todos los valores deben estar completos y la matricula debe tener el formato: MA-1234"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Veterinario actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos"),
            @ApiResponse(responseCode = "404", description = "No se encontró un veterinario con el ID indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateVeterinario(
            @Parameter(description = "ID del veterinario a actualizar", example = "1")
            @PathVariable Long id, @Valid @RequestBody VeterinarioDTO veterinarioDTO) {
        return ResponseEntity.ok(veterinarioService.updateVeterinario(id, veterinarioDTO));
    }

    @Operation(summary = "Borrar un veterinario según su ID",
            description = "Borra el veterinario del ID ingresado."
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
        veterinarioService.deleteVeterinario(id);
        return ResponseEntity.noContent().build();
    }
}
