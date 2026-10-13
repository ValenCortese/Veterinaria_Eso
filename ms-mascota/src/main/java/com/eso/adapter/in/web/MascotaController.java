package com.eso.adapter.in.web;

import com.eso.adapter.in.web.dto.MascotaRequest;
import com.eso.adapter.in.web.dto.MascotaResponse;
import com.eso.domain.port.in.MascotaService;
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

@Tag(name="Mascotas", description = "CRUD - Veterinaria - Mascota")
@RestController
@RequestMapping("/api/mascotas")
@RequiredArgsConstructor
public class MascotaController {

    private final MascotaService mascotaService;

    @Operation(summary = "Lista todas las mascotas",
            description = "Devuelve una lista conteniendo todas las mascotas y su información."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de mascotas obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<MascotaResponse>> getAllMascotas() {
        return ResponseEntity.ok(mascotaService.getAllMascotas());
    }

    @Operation(summary = "Busca una mascota por ID",
            description = "Devuelve toda la información registrada al ID de la mascota buscado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mascota encontrada correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró una mascota con el ID indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MascotaResponse> getMascotaById(
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
    public ResponseEntity<MascotaResponse> createMascota(@Valid @RequestBody MascotaRequest mascotaRequest) {
        // Al enviar mascotaRequest a save(), el servicio ya podrá extraer el duenioId usando mascotaRequest.getDuenioId()
        return ResponseEntity.status(HttpStatus.CREATED).body(mascotaService.save(mascotaRequest));
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
    public ResponseEntity<MascotaResponse> updateMascota(
            @Parameter(description = "ID de la mascota a actualizar", example = "1")
            @PathVariable Long id, @Valid @RequestBody MascotaRequest mascotaRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(mascotaService.update(id, mascotaRequest));
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