package com.veterinariaEso.Controller;

import com.veterinariaEso.DTO.MedicamentoRequestDTO;
import com.veterinariaEso.DTO.MedicamentoResponseDTO;
import com.veterinariaEso.Service.MedicamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Medicamentos", description = "CRUD de medicamentos")
@RestController
@RequestMapping("/api/medicamentos")
@RequiredArgsConstructor
public class MedicamentoController {

    @Autowired
    private final MedicamentoService medicamentoService;

    @Operation(summary = "Lista todos los medicamentos",
            description = "Devuelve todos los medicamentos registrados con su información y stock disponible.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de medicamentos obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<MedicamentoResponseDTO>> getAllMedicamentos() {
        return ResponseEntity.ok(medicamentoService.getAllMedicamentos());
    }

    @Operation(summary = "Busca un medicamento por ID",
            description = "Devuelve la información del medicamento correspondiente al ID indicado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Medicamento encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró un medicamento con el ID indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoResponseDTO> getMedicamentoById(
            @Parameter(description = "ID del medicamento", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(medicamentoService.getMedicamentoById(id));
    }

    @Operation(summary = "Registra un medicamento",
            description = "Crea un medicamento validando nombre, principio activo, stock y precio unitario.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Medicamento creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<MedicamentoResponseDTO> createMedicamento(
            @Valid @RequestBody MedicamentoRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(medicamentoService.createMedicamento(requestDTO));
    }

    @Operation(summary = "Actualiza un medicamento según su ID",
            description = "Reemplaza los datos del medicamento indicado, validando todos los campos recibidos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Medicamento actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos"),
            @ApiResponse(responseCode = "404", description = "No se encontró un medicamento con el ID indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MedicamentoResponseDTO> updateMedicamento(
            @Parameter(description = "ID del medicamento a actualizar", example = "1")
            @PathVariable Long id, @Valid @RequestBody MedicamentoRequestDTO requestDTO) {
        return ResponseEntity.ok(medicamentoService.updateMedicamento(id, requestDTO));
    }

    @Operation(summary = "Elimina un medicamento según su ID",
            description = "Elimina el medicamento correspondiente al ID indicado.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Medicamento eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró un medicamento con el ID indicado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicamento(
            @Parameter(description = "ID del medicamento a eliminar", example = "1")
            @PathVariable Long id) {
        medicamentoService.deleteMedicamento(id);
        return ResponseEntity.noContent().build();
    }
}
