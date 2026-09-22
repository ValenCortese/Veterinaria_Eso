package com.veterinariaEso.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VeterinarioDTO {

    @Schema(description = "Identificador único del veterinario", example = "1")
    @NotNull
    @Min(1)
    @Positive
    private Long id;

    @Schema(description = "Nombre del veterinario", example = "Carlos")
    @NotBlank(message= "El nombre no puede estar vacío o contener solo espacios")
    private String nombre;

    @Schema(description = "Apellido del veterinario", example = "Fernández")
    @NotBlank(message= "El apellido no puede estar vacío o contener solo espacios")
    private String apellido;

    @Schema(description = "Matrícula profesional del veterinario con formato AA-1234", example = "MN-2045")
    @NotBlank(message= "La matricula no puede estar vacía o contener solo espacios")
    @Pattern(regexp = "^[A-Z]{2}-\\d{4}$", message = "Formato inválido. Ejemplo aceptado: MA-1234")
    private String matricula;

    @Schema(description = "Especialidad médica del veterinario", example = "Cardiología")
    @NotBlank(message= "La especialidad no puede estar vacía o contener solo espacios")
    private String especialidad;
}
