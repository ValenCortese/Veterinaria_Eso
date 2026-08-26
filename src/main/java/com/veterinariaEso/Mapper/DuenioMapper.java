package com.veterinariaEso.Mapper;

import com.veterinariaEso.DTO.DuenioDTO;
import com.veterinariaEso.Model.Duenio;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DuenioMapper {

    DuenioDTO toDuenioDTO(Duenio duenio);
    Duenio toDuenio(DuenioDTO duenioDTO);
}
