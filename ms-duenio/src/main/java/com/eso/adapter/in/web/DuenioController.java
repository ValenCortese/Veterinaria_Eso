package com.eso.adapter.in.web;

import com.eso.application.dto.DuenioRequest;
import com.eso.application.dto.DuenioResponse;
import com.eso.domain.port.in.DuenioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Duenios", description = "CRUD - Veterinaria - Duenio")
@RestController
@RequestMapping("/api/duenio")
public class DuenioController {

    @Autowired
    private DuenioService duenioService;

    @GetMapping
    @Operation(summary = "Lista todos los dueños", description = "Devuelve una lista con todos los dueños y su información.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de dueños obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<DuenioResponse>> getAllDuenios() {
        return ResponseEntity.ok(duenioService.listAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca un dueño por ID", description = "Devuelve toda la información del dueño correspondiente al ID indicado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dueño encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró un dueño con el ID indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<DuenioResponse> getDuenioById(
            @Parameter(description = "ID del dueño", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(duenioService.findById(id));
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Busca un dueño por email")
    public ResponseEntity<DuenioResponse> getDuenioByEmail(
            @Parameter(description = "Correo electrónico del dueño", example = "maria.gomez@example.com")
            @PathVariable String email) {
        return ResponseEntity.ok(duenioService.findByEmail(email));
    }

    @GetMapping("/nombre/{nombre}")
    @Operation(summary = "Busca un dueño por nombre")
    public ResponseEntity<DuenioResponse> getDuenioByNombre(
            @Parameter(description = "Nombre del dueño", example = "María")
            @RequestParam String nombre){
        return ResponseEntity.ok(duenioService.findByNombre(nombre));
    }

    @PostMapping
    @Operation(summary = "Registra un nuevo dueño")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Dueño creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos"),
            @ApiResponse(responseCode = "409", description = "La cédula ya se encuentra registrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<DuenioResponse> createDuenio(@Valid @RequestBody DuenioRequest duenioRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(duenioService.save(duenioRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un dueño según su ID")
    public ResponseEntity<DuenioResponse> updateDuenio(
            @Parameter(description = "ID del dueño a actualizar", example = "1")
            @PathVariable Long id, @Valid @RequestBody DuenioRequest duenioRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(duenioService.update(id, duenioRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un dueño según su ID")
    public ResponseEntity<Void> deleteDuenio(
            @Parameter(description = "ID del dueño a eliminar", example = "1")
            @PathVariable Long id) {
        duenioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}