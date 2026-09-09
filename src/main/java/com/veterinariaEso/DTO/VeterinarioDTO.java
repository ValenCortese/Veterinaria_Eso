package com.veterinariaEso.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VeterinarioDTO {

    @NotNull
    @Min(1)
    @Positive
    private Long id;

    @NotBlank(message= "El nombre no puede estar vacío o contener solo espacios")
    private String nombre;

    @NotBlank(message= "El apellido no puede estar vacío o contener solo espacios")
    private String apellido;

    @NotBlank(message= "La matricula no puede estar vacía o contener solo espacios")
    @Pattern(regexp = "^[A-Z]{2}-\\d{4}$", message = "Formato inválido. Ejemplo aceptado: MA-1234")
    private String matricula;

    @NotBlank(message= "La especialidad no puede estar vacía o contener solo espacios")
    private String especialidad;
}
