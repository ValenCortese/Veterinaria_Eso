package com.eso.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La especie es obligatoria")
    private String especie;

    private String raza;

    private LocalDate fechaNacimiento;

    @NotNull(message = "El ID del dueño es obligatorio")
    private Long duenioId;
}