package com.veterinariaEso.DTO;

import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurnoRequestDTO {

    @Schema(description = "Fecha del turno; debe ser la fecha actual o una fecha futura", example = "2026-10-15")
    @FutureOrPresent(message= "La fecha tiene que ser hoy o en el futuro")
    private LocalDate fecha;

    @Schema(description = "Hora del turno", example = "14:30:00")
    @Min(value = 0, message = "La hora debe ser entre 0 y 23")
    @Max(value = 23, message = "La hora debe ser entre 0 y 23")
    private LocalTime hora;

    @Schema(description = "Motivo de la consulta", example = "Control anual")
    private String motivo;

    @Schema(description = "Identificador de la mascota que será atendida", example = "5")
    @NotNull
    @Min(1)
    @Positive
    private Long mascotaId;

    @Schema(description = "Identificador del veterinario asignado", example = "2")
    @NotNull
    @Min(1)
    @Positive
    private Long veterinarioId;
}
