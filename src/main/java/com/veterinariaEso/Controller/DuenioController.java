package com.veterinariaEso.Controller;

import com.veterinariaEso.DTO.DuenioDTO;
import com.veterinariaEso.Exception.ResourceNotFoundException;
import com.veterinariaEso.Service.DuenioService;
import com.veterinariaEso.Service.MascotaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name="Duenios", description = "CRUD - Veterinaria Eso")
@RestController
@RequestMapping("/api/duenios")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DuenioController {

    @Autowired
    private final DuenioService duenioService;
    @Autowired
    private final MascotaService mascotaService;

    @GetMapping
    @Operation(summary = "Lista todos los dueños",
            description = "Devuelve una lista con todos los dueños y su información.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de dueños obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<DuenioDTO>> getAllDuenios() {
        return ResponseEntity.ok(duenioService.getAllDuenios());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca un dueño por ID",
            description = "Devuelve toda la información del dueño correspondiente al ID indicado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dueño encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró un dueño con el ID indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> getDuenioById(
            @Parameter(description = "ID del dueño", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(duenioService.getDuenioById(id));
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Busca un dueño por email",
            description = "Devuelve la información del dueño asociado al email indicado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dueño encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró un dueño con el email indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<DuenioDTO> getDuenioByEmail(
            @Parameter(description = "Correo electrónico del dueño", example = "maria.gomez@example.com")
            @PathVariable String email) {
        return ResponseEntity.ok(duenioService.getDuenioByEmail(email));
    }

    @GetMapping("{id}/mascotas")
    @Operation(summary = "Lista las mascotas de un dueño",
            description = "Devuelve todas las mascotas asociadas al dueño indicado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mascotas obtenidas correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró un dueño con el ID indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> getMascotasByDuenio(
            @Parameter(description = "ID del dueño", example = "1")
            @PathVariable Long id){
        return ResponseEntity.ok(mascotaService.getMascotaByDuenio(id));
    }

    @GetMapping("/nombre/{nombre}")
    @Operation(summary = "Busca un dueño por nombre",
            description = "Devuelve la información del dueño asociado al nombre indicado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dueño encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró un dueño con el nombre indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<DuenioDTO> getDuenioByNombre(
            @Parameter(description = "Nombre del dueño", example = "María")
            @RequestParam String nombre){
        return ResponseEntity.ok(duenioService.getDuenioByNombre(nombre));
    }

    @PostMapping
    @Operation(summary = "Registra un nuevo dueño",
            description = "Crea un dueño con los datos proporcionados.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Dueño creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos"),
            @ApiResponse(responseCode = "409", description = "La cédula ya se encuentra registrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> createDuenio(@Valid @RequestBody DuenioDTO duenioDTO) {
        DuenioDTO nuevo = duenioService.createDuenio(duenioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un dueño según su ID",
            description = "Actualiza los datos del dueño correspondiente al ID indicado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dueño actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos"),
            @ApiResponse(responseCode = "404", description = "No se encontró un dueño con el ID indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> updateDuenio(
            @Parameter(description = "ID del dueño a actualizar", example = "1")
            @PathVariable Long id, @Valid @RequestBody DuenioDTO duenioDTO) {
        return ResponseEntity.ok(duenioService.updateDuenio(id, duenioDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un dueño según su ID",
            description = "Elimina el dueño correspondiente al ID indicado.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Dueño eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró un dueño con el ID indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> deleteDuenio(
            @Parameter(description = "ID del dueño a eliminar", example = "1")
            @PathVariable Long id) {
        duenioService.deleteDuenio(id);
        return ResponseEntity.noContent().build();
    }
}
