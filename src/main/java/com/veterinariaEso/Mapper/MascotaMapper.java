package com.veterinariaEso.Mapper;

import com.veterinariaEso.DTO.MascotaDTO;
import com.veterinariaEso.Model.Mascota;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MascotaMapper {

    @Mapping(source = "duenio.id", target = "duenioId")
    @Mapping(source = "duenio.nombre", target = "duenioNombre")
    MascotaDTO toMascotaDTO(Mascota mascota);
    Mascota toMascota(MascotaDTO mascotaDTO);
}
