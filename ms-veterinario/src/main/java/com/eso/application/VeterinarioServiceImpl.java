package com.eso.application;

import com.eso.adapter.in.web.dto.VeterinarioMapper;
import com.eso.adapter.in.web.dto.VeterinarioRequest;
import com.eso.adapter.in.web.dto.VeterinarioResponse;
import com.eso.domain.exception.DuplicateResourceException;
import com.eso.domain.exception.ResourceNotFoundException;
import com.eso.domain.model.Veterinario;
import com.eso.domain.port.in.VeterinarioService;
import com.eso.domain.port.out.VeterinarioRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VeterinarioServiceImpl implements VeterinarioService {

    private final VeterinarioRepositoryPort veterinarioRepository;

    @Override
    public List<VeterinarioResponse> findAll() {
        return veterinarioRepository.findAll().stream()
                .map(VeterinarioMapper::toResponse)
                .toList();
    }

    @Override
    public VeterinarioResponse findById(Long id) {
        return VeterinarioMapper.toResponse(obtenerEntidad(id));
    }

    @Override
    public List<VeterinarioResponse> findByEspecialidad(String especialidad) {
        return veterinarioRepository.findByEspecialidad(especialidad).stream()
                .map(VeterinarioMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public VeterinarioResponse save(VeterinarioRequest request) {
        if (veterinarioRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("El email ya está registrado.");
        }
        if (veterinarioRepository.existsByMatricula(request.getMatricula())) {
            throw new DuplicateResourceException("La matrícula ya está registrada.");
        }

        Veterinario guardado = veterinarioRepository.save(VeterinarioMapper.toEntity(request));
        return VeterinarioMapper.toResponse(guardado);
    }

    @Override
    @Transactional
    public VeterinarioResponse update(Long id, VeterinarioRequest request) {
        Veterinario existente = obtenerEntidad(id);

        existente.setNombre(request.getNombre());
        existente.setApellido(request.getApellido());
        existente.setMatricula(request.getMatricula());
        existente.setEspecialidad(request.getEspecialidad());
        existente.setEmail(request.getEmail());

        return VeterinarioMapper.toResponse(veterinarioRepository.save(existente));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        obtenerEntidad(id);
        veterinarioRepository.deleteById(id);
    }

    private Veterinario obtenerEntidad(Long id) {
        Veterinario veterinario = veterinarioRepository.findById(id);
        if (veterinario == null) {
            throw new ResourceNotFoundException("No se encontró al veterinario con ID: " + id);
        }
        return veterinario;
    }
}