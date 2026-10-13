package com.eso.adapter.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeterinarioRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @Pattern(
            regexp = "^[A-Z]{2,3}-\\d{4,6}$",
            message = "Formato inválido. Ejemplo aceptado: MAT-123456"
    )
    @NotBlank(message = "La matrícula es obligatoria")
    private String matricula;

    @NotBlank(message = "La especialidad es obligatoria")
    private String especialidad;

    @Email(message = "El formato del email es inválido")
    @NotBlank(message = "El email es obligatorio")
    private String email;
}