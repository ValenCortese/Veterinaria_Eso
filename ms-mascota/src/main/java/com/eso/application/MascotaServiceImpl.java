package com.eso.application;

import com.eso.adapter.in.web.dto.MascotaMapper;
import com.eso.adapter.in.web.dto.MascotaRequest;
import com.eso.adapter.in.web.dto.MascotaResponse;
import com.eso.domain.exception.DuplicateResourceException;
import com.eso.domain.exception.MascotaNotFoundException;
import com.eso.domain.model.Mascota;
import com.eso.domain.port.in.MascotaService;
import com.eso.domain.port.out.MascotaRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MascotaServiceImpl implements MascotaService {

    private final MascotaRepositoryPort mascotaRepository;

    @Override
    public List<MascotaResponse> getAllMascotas() {
        return mascotaRepository.findAll().stream()
                .map(MascotaMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public MascotaResponse save(MascotaRequest mascotaRequest) {
        Mascota mascota = MascotaMapper.toEntity(mascotaRequest);

        if (mascotaRepository.existsByNombreAndDuenioId(mascota.getNombre(), mascota.getDuenioId())) {
            throw new DuplicateResourceException("El dueño ya tiene una mascota registrada con el nombre: " + mascota.getNombre());
        }

        Mascota mascotaGuardada = mascotaRepository.save(mascota);
        return MascotaMapper.toResponse(mascotaGuardada);
    }

    @Override
    @Transactional
    public MascotaResponse update(Long id, MascotaRequest mascotaRequest) {
        Mascota mascotaExistente = obtenerEntidadMascota(id);

        mascotaExistente.setNombre(mascotaRequest.getNombre());
        mascotaExistente.setEspecie(mascotaRequest.getEspecie());
        mascotaExistente.setRaza(mascotaRequest.getRaza());
        mascotaExistente.setFechaNacimiento(mascotaRequest.getFechaNacimiento());
        mascotaExistente.setDuenioId(mascotaRequest.getDuenioId());

        Mascota mascotaActualizada = mascotaRepository.save(mascotaExistente);
        return MascotaMapper.toResponse(mascotaActualizada);
    }

    @Override
    @Transactional
    public void deleteMascota(Long id) {
        obtenerEntidadMascota(id);
        mascotaRepository.deleteById(id);
    }

    @Override
    public MascotaResponse getMascotaById(Long id) {
        return MascotaMapper.toResponse(obtenerEntidadMascota(id));
    }

    @Override
    public MascotaResponse findByNombre(String nombre) {
        Mascota mascota = mascotaRepository.findByNombre(nombre);
        if (mascota == null) {
            throw new MascotaNotFoundException("No se encontró mascota con el nombre: " + nombre);
        }
        return MascotaMapper.toResponse(mascota);
    }

    @Override
    public List<MascotaResponse> findByDuenioId(Long duenioId) {
        return mascotaRepository.findByDuenioId(duenioId).stream()
                .map(MascotaMapper::toResponse)
                .toList();
    }

    private Mascota obtenerEntidadMascota(Long id) {
        Mascota mascota = mascotaRepository.findById(id);
        if (mascota == null) {
            throw new MascotaNotFoundException("No se encontró mascota con ID: " + id);
        }
        return mascota;
    }
}