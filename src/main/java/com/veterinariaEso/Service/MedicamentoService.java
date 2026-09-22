package com.veterinariaEso.Service;

import com.veterinariaEso.DTO.MedicamentoRequestDTO;
import com.veterinariaEso.DTO.MedicamentoResponseDTO;
import com.veterinariaEso.Exception.ResourceNotFoundException;
import com.veterinariaEso.Exception.StockInsuficienteException;
import com.veterinariaEso.Mapper.MedicamentoMapper;
import com.veterinariaEso.Model.Medicamento;
import com.veterinariaEso.Model.Turno;
import com.veterinariaEso.Repository.MedicamentoRepository;
import com.veterinariaEso.Repository.TurnoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;
    private final TurnoRepository turnoRepository;
    private final MedicamentoMapper medicamentoMapper;

    public List<MedicamentoResponseDTO> getAllMedicamentos() {
        return medicamentoRepository.findAll().stream()
                .map(medicamentoMapper::toMedicamentoResponseDTO)
                .toList();
    }

    public MedicamentoResponseDTO getMedicamentoById(Long id) {
        return medicamentoMapper.toMedicamentoResponseDTO(findMedicamento(id));
    }

    @Transactional
    public List<MedicamentoResponseDTO> getMedicamentosByTurno(Long turnoId) {
        Turno turno = findTurno(turnoId);
        return turno.getMedicamentos().stream()
                .map(medicamentoMapper::toMedicamentoResponseDTO)
                .toList();
    }

    @Transactional
    public MedicamentoResponseDTO associateToTurno(Long turnoId, Long medicamentoId) {
        Turno turno = findTurno(turnoId);
        Medicamento medicamento = findMedicamento(medicamentoId);
        if (medicamento.getStock() == null || medicamento.getStock() <= 0) {
            throw new StockInsuficienteException(medicamentoId);
        }

        turno.getMedicamentos().add(medicamento);
        medicamento.setStock(medicamento.getStock() - 1);
        medicamentoRepository.save(medicamento);
        turnoRepository.save(turno);
        return medicamentoMapper.toMedicamentoResponseDTO(medicamento);
    }

    @Transactional
    public MedicamentoResponseDTO createMedicamento(MedicamentoRequestDTO requestDTO) {
        Medicamento medicamento = medicamentoMapper.toMedicamento(requestDTO);
        return medicamentoMapper.toMedicamentoResponseDTO(medicamentoRepository.save(medicamento));
    }

    @Transactional
    public MedicamentoResponseDTO updateMedicamento(Long id, MedicamentoRequestDTO requestDTO) {
        Medicamento medicamento = findMedicamento(id);
        medicamento.setNombre(requestDTO.getNombre());
        medicamento.setPrincipioActivo(requestDTO.getPrincipioActivo());
        medicamento.setStock(requestDTO.getStock());
        medicamento.setPrecioUnitario(requestDTO.getPrecioUnitario());
        return medicamentoMapper.toMedicamentoResponseDTO(medicamentoRepository.save(medicamento));
    }

    @Transactional
    public void deleteMedicamento(Long id) {
        if (!medicamentoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Medicamento", id);
        }
        medicamentoRepository.deleteById(id);
    }

    private Medicamento findMedicamento(Long id) {
        return medicamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicamento", id));
    }

    private Turno findTurno(Long id) {
        return turnoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turno", id));
    }
}
