package com.veterinariaEso.Service;

import com.veterinariaEso.DTO.DuenioDTO;
import com.veterinariaEso.Exception.ResourceNotFoundException;
import com.veterinariaEso.Mapper.DuenioMapper;
import com.veterinariaEso.Model.Duenio;
import com.veterinariaEso.Repository.DuenioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DuenioService{

    private final DuenioRepository duenioRepository;
    private final DuenioMapper duenioMapper;

    public List<DuenioDTO> getAllDuenios() {
        return duenioRepository.findAll().stream().map(duenioMapper::toDuenioDTO).collect(Collectors.toList());
    }

    public DuenioDTO getDuenioById(Long id) {
        Duenio duenio = duenioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Duenio", id));
        return duenioMapper.toDuenioDTO(duenio);
    }

    public DuenioDTO getDuenioByEmail(String email) {
        Duenio duenio = duenioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No se encontró un dueño con el email: " + email));
        return duenioMapper.toDuenioDTO(duenio);
    }

    public DuenioDTO getDuenioByNombre(String nombre) {
        Duenio duenio = duenioRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("No se encontró dueño con el nombre: " + nombre));
        return duenioMapper.toDuenioDTO(duenio);
    }

    @Transactional
    public DuenioDTO createDuenio(DuenioDTO duenioDTO) {
        if (duenioRepository.existsByCedula(duenioDTO.getCedula())) {
            throw new RuntimeException("Ya existe un dueño con Cedula: " + duenioDTO.getCedula());
        }
        Duenio duenio = duenioMapper.toDuenio(duenioDTO);
        return duenioMapper.toDuenioDTO(duenioRepository.save(duenio));
    }

    public DuenioDTO updateDuenio(Long id, DuenioDTO duenioDTO) {
        Duenio duenio = duenioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Duenio", id));
        duenio.setNombre(duenioDTO.getNombre());
        duenio.setApellido(duenioDTO.getApellido());
        duenio.setTelefono(duenioDTO.getTelefono());
        duenio.setEmail(duenioDTO.getEmail());
        return duenioMapper.toDuenioDTO(duenioRepository.save(duenio));
    }

    public void deleteDuenio(Long id) {
        if (!duenioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Duenio", id);
        }
        duenioRepository.deleteById(id);
    }
}