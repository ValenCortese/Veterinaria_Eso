package com.veterinariaEso.Mapper;

import com.veterinariaEso.DTO.MedicamentoRequestDTO;
import com.veterinariaEso.DTO.MedicamentoResponseDTO;
import com.veterinariaEso.Model.Medicamento;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicamentoMapper {
    MedicamentoResponseDTO toMedicamentoResponseDTO(Medicamento medicamento);
    Medicamento toMedicamento(MedicamentoRequestDTO requestDTO);
}
