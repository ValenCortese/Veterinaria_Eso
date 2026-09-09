package com.veterinariaEso.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MascotaDTO {

    @NotNull
    @Min(1)
    @Positive
    private Long id;

    @NotBlank(message= "El nombre no puede estar vacío o contener solo espacios")
    private String nombre;

    @NotBlank(message= "La especie no puede estar vacía o contener solo espacios")
    private String especie;

    @NotBlank(message= "La raza no puede estar vacía o contener solo espacios")
    private String raza;

    @Past(message= "La fecha de nacimiento solo puede ser anterior al dia de hoy")
    private LocalDate fechaNacimiento;

    @NotNull
    @Min(1)
    @Positive
    private Long duenioId;

    @NotBlank
    private String duenioNombre;
}
