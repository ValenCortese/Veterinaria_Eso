package com.veterinariaEso.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurnoRequestDTO {

    @FutureOrPresent(message= "La fecha tiene que ser hoy o en el futuro")
    private LocalDate fecha;

    @Min(value = 0, message = "La hora debe ser entre 0 y 23")
    @Max(value = 23, message = "La hora debe ser entre 0 y 23")
    private LocalTime hora;

    private String motivo;

    @NotNull
    @Min(1)
    @Positive
    private Long mascotaId;

    @NotNull
    @Min(1)
    @Positive
    private Long veterinarioId;
}
