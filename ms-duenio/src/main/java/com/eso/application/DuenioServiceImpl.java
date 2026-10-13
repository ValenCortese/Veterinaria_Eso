package com.eso.application;

import com.eso.adapter.in.web.dto.DuenioMapper;
import com.eso.adapter.in.web.dto.DuenioRequest;
import com.eso.adapter.in.web.dto.DuenioResponse;
import com.eso.domain.exception.DuenioNotFoundException;
import com.eso.domain.exception.DuplicateEmailException;
import com.eso.domain.model.Duenio;
import com.eso.domain.port.in.DuenioService;
import com.eso.domain.port.out.DuenioRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DuenioServiceImpl implements DuenioService {

    private final DuenioRepositoryPort duenioRepository;

    @Override
    public List<DuenioResponse> listAll() {
        return duenioRepository.findAll().stream()
                .map(DuenioMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public DuenioResponse save(DuenioRequest duenioRequest) {
        Duenio duenio = DuenioMapper.toEntity(duenioRequest);

        if (duenioRepository.existsByEmail(duenio.getEmail())) {
            throw new DuplicateEmailException(duenio.getEmail());
        }

        Duenio duenioGuardado = duenioRepository.save(duenio);
        return DuenioMapper.toResponse(duenioGuardado);
    }

    @Override
    @Transactional
    public DuenioResponse update(Long id, DuenioRequest duenioRequest) {
        Duenio duenioExistente = obtenerEntidadDuenio(id);

        duenioExistente.setNombre(duenioRequest.getNombre());
        duenioExistente.setApellido(duenioRequest.getApellido());
        duenioExistente.setTelefono(duenioRequest.getTelefono());
        duenioExistente.setCedula(duenioRequest.getCedula());
        duenioExistente.setEmail(duenioRequest.getEmail());

        Duenio duenioActualizado = duenioRepository.save(duenioExistente);
        return DuenioMapper.toResponse(duenioActualizado);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        obtenerEntidadDuenio(id);
        duenioRepository.deleteById(id);
    }

    @Override
    public DuenioResponse findById(Long id) {
        return DuenioMapper.toResponse(obtenerEntidadDuenio(id));
    }

    @Override
    public DuenioResponse findByEmail(String email) {
        Duenio duenio = duenioRepository.findByEmail(email);
        if (duenio == null) {
            throw new DuenioNotFoundException("email", email);
        }
        return DuenioMapper.toResponse(duenio);
    }

    @Override
    public DuenioResponse findByNombre(String nombre) {
        Duenio duenio = duenioRepository.findByNombre(nombre);
        if (duenio == null) {
            throw new DuenioNotFoundException("nombre", nombre);
        }
        return DuenioMapper.toResponse(duenio);
    }
    /**
     * Devuelve la Entidad pura de Dominio para uso interno de esta clase.
     */
    private Duenio obtenerEntidadDuenio(Long id) {
        Duenio duenio = duenioRepository.findById(id);
        if (duenio == null) {
            throw new DuenioNotFoundException(id);
        }
        return duenio;
    }
}