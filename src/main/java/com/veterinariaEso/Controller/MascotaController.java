package com.veterinariaEso.Controller;

import com.veterinariaEso.DTO.MascotaDTO;
import com.veterinariaEso.Exception.ResourceNotFoundException;
import com.veterinariaEso.Service.MascotaService;
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

@Tag(name="Mascotas", description = "CRUD - Veterinaria Eso")
@RestController
@RequestMapping("/api/mascotas")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MascotaController {

    @Autowired
    private final MascotaService mascotaService;

    @Operation(summary = "Lista todos las mascotas",
            description = "Devuelve una lista conteniendo todos las mascotas y su información."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de mascotas obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<MascotaDTO>> getAllMascotas() {
        return ResponseEntity.ok(mascotaService.getAllMascotas());
    }

    @Operation(summary = "Busca un mascota por ID",
            description = "Devuelve toda la información registrada al ID de la mascota buscado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mascota encontrada correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró una mascota con el ID indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getMascotaById(
            @Parameter(description = "ID de la mascota", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(mascotaService.getMascotaById(id));
    }

    @Operation(summary = "Registra una nueva mascota",
            description = "Crea una nueva mascota. Todos los valores deben estar completos y la fecha de nacimiento solo puede ser anterior al dia de hoy"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Mascota creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos"),
            @ApiResponse(responseCode = "404", description = "No se encontró el dueño indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> createMascota(
            @Parameter(description = "ID del dueño asociado a la mascota", example = "1")
            @RequestParam Long duenioId, @Valid @RequestBody MascotaDTO mascotaDTO) {
        MascotaDTO nueva = mascotaService.createMascota(duenioId, mascotaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @Operation(summary = "Actualizar una mascota según su ID",
            description = "Actualiza la mascota del ID ingresado. Todos los valores deben estar completos y la fecha de nacimiento solo puede ser anterior al dia de hoy"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mascota actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos"),
            @ApiResponse(responseCode = "404", description = "No se encontró una mascota con el ID indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMascota(
            @Parameter(description = "ID de la mascota a actualizar", example = "1")
            @PathVariable Long id, @Valid @RequestBody MascotaDTO MascotaDTO) {
        return ResponseEntity.ok(mascotaService.updateMascota(id, MascotaDTO));
    }

    @Operation(summary = "Borrar una mascota según su ID",
            description = "Borra la mascota del ID ingresado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Mascota eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró una mascota con el ID indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMascota(
            @Parameter(description = "ID de la mascota a eliminar", example = "1")
            @PathVariable Long id) {
        mascotaService.deleteMascota(id);
        return ResponseEntity.noContent().build();
    }
}
