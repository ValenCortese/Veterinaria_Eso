package com.veterinariaEso.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DuenioDTO {

    @NotNull
    @Min(1)
    @Positive
    private Long id;

    @NotBlank(message= "El nombre no puede estar vacío o contener solo espacios")
    private String nombre;

    @NotBlank(message= "El apellido no puede estar vacío o contener solo espacios")
    private String apellido;

    @NotBlank(message= "La cedula no puede estar vacía o contener solo espacios")
    @Size(min = 7, max = 8, message= "La cedula debe tener mínimo 7 caracteres y máximo 8")
    private String cedula;

    private Integer telefono;

    @NotBlank(message= "El email no puede estar vacío o contener solo espacios")
    @Email(message = "El formato no es valido")
    private String email;
}
