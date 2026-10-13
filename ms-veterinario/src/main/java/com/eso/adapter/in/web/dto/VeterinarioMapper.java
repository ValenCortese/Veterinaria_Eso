package com.eso.adapter.in.web.dto;

import com.eso.domain.model.Veterinario;

public final class VeterinarioMapper {

    private VeterinarioMapper() {}

    public static Veterinario toEntity(VeterinarioRequest request) {
        if (request == null) return null;
        Veterinario veterinario = new Veterinario();
        veterinario.setNombre(request.getNombre());
        veterinario.setApellido(request.getApellido());
        veterinario.setMatricula(request.getMatricula());
        veterinario.setEspecialidad(request.getEspecialidad());
        veterinario.setEmail(request.getEmail());
        return veterinario;
    }

    public static VeterinarioResponse toResponse(Veterinario veterinario) {
        if (veterinario == null) return null;
        return new VeterinarioResponse(
                veterinario.getId(),
                veterinario.getNombre(),
                veterinario.getApellido(),
                veterinario.getMatricula(),
                veterinario.getEspecialidad(),
                veterinario.getEmail()
        );
    }
}