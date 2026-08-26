package com.veterinariaEso.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurnoRequestDTO {

    private LocalDate fecha;
    private LocalTime hora;
    private String motivo;
    private Long mascotaId;
    private Long veterinarioId;
}
