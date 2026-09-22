package com.veterinariaEso.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DuenioDTO {

    @Schema(description = "Identificador único del dueño", example = "1")
    @Min(1)
    @Positive
    private Long id;

    @Schema(description = "Nombre del dueño", example = "María")
    @NotBlank(message= "El nombre no puede estar vacío o contener solo espacios")
    private String nombre;

    @Schema(description = "Apellido del dueño", example = "Gómez")
    @NotBlank(message= "El apellido no puede estar vacío o contener solo espacios")
    private String apellido;

    @Schema(description = "Número de cédula del dueño", example = "35123456")
    @NotBlank(message= "La cedula no puede estar vacía o contener solo espacios")
    @Size(min = 7, max = 8, message= "La cedula debe tener mínimo 7 caracteres y máximo 8")
    private String cedula;

    @Schema(description = "Número de teléfono del dueño", example = "1123456789")
    private Integer telefono;

    @Schema(description = "Correo electrónico del dueño", example = "maria.gomez@example.com")
    @NotBlank(message= "El email no puede estar vacío o contener solo espacios")
    @Email(message = "El formato no es valido")
    private String email;
}
