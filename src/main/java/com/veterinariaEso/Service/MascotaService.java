package com.veterinariaEso.Service;

import com.veterinariaEso.DTO.MascotaDTO;
import com.veterinariaEso.Exception.ResourceNotFoundException;
import com.veterinariaEso.Mapper.MascotaMapper;
import com.veterinariaEso.Model.Duenio;
import com.veterinariaEso.Model.Mascota;
import com.veterinariaEso.Repository.DuenioRepository;
import com.veterinariaEso.Repository.MascotaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MascotaService {

    private final MascotaRepository mascotaRepository;
    private final DuenioRepository duenioRepository;
    private final MascotaMapper mascotaMapper;

    public List<MascotaDTO> getAllMascotas() {
        return mascotaRepository.findAll().stream().map(mascotaMapper::toMascotaDTO).collect(Collectors.toList());
    }

    public MascotaDTO getMascotaById(Long id) {
        Mascota mascota = mascotaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Mascota", id));
        return mascotaMapper.toMascotaDTO(mascota);
    }

    @Transactional // este
    public List<MascotaDTO> getMascotaByDuenio(Long duenioId) {
        if (!duenioRepository.existsById(duenioId)) {
            throw new ResourceNotFoundException("Duenio", duenioId);
        }
        return mascotaRepository.findByDuenioId(duenioId).stream()
                .map(mascotaMapper::toMascotaDTO).collect(Collectors.toList());
    }

    @Transactional
    public MascotaDTO createMascota(Long duenioId, MascotaDTO mascotaDTO) {
        Duenio duenio = duenioRepository.findById(duenioId)
                .orElseThrow(() -> new ResourceNotFoundException("Duenio", duenioId));
        Mascota mascota = mascotaMapper.toMascota(mascotaDTO);
        mascota.setDuenio(duenio);
        return mascotaMapper.toMascotaDTO(mascotaRepository.save(mascota));
    }

    @Transactional
    public MascotaDTO updateMascota(Long id, MascotaDTO mascotaDTO) {
        Mascota mascota = mascotaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Mascota", id));
        mascota.setNombre(mascotaDTO.getNombre());
        mascota.setEspecie(mascotaDTO.getEspecie());
        mascota.setRaza(mascotaDTO.getRaza());
        mascota.setFechaNacimiento(mascotaDTO.getFechaNacimiento());
        return mascotaMapper.toMascotaDTO(mascotaRepository.save(mascota));
    }

    public void deleteMascota(Long id) {
        if (!mascotaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Mascota", id);
        }
        mascotaRepository.deleteById(id);
    }
}
