package com.veterinariaEso.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicamentoRequestDTO {

    @Schema(description = "Nombre comercial del medicamento", example = "Amoxicilina")
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @Schema(description = "Principio activo del medicamento", example = "Amoxicilina trihidrato")
    @NotBlank(message = "El principio activo no puede estar vacío")
    private String principioActivo;

    @Schema(description = "Cantidad disponible", example = "25")
    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @Schema(description = "Precio unitario", example = "1250.50")
    @NotNull(message = "El precio unitario es obligatorio")
    @DecimalMin(value = "0.0", message = "El precio no puede ser negativo")
    private BigDecimal precioUnitario;
}
