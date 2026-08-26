package com.veterinariaEso.Mapper;

import com.veterinariaEso.DTO.VeterinarioDTO;
import com.veterinariaEso.Model.Mascota;
import com.veterinariaEso.Model.Veterinario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VeterinarioMapper {

    VeterinarioDTO toVeterinarioDTO(Veterinario veterinario);
    Veterinario toVeterinario(VeterinarioDTO veterinarioDTO);
}
