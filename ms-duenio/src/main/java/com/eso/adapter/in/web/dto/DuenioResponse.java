package com.eso.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DuenioResponse {
    private Long id;
    private String nombre;
    private String apellido;
    private String cedula;
    private Integer telefono;
    private String email;
}
