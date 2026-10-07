package com.eso.application;

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
    public List<Duenio> listAll() {
        return duenioRepository.findAll();
    }

    @Override
    @Transactional
    public Duenio save(Duenio duenio) {
        if (duenioRepository.existsByEmail(duenio.getEmail())) {
            throw new DuplicateEmailException(duenio.getEmail());
        }
        return duenioRepository.save(duenio);
    }

    @Override
    @Transactional
    public Duenio update(Long id, Duenio duenio) {
        Duenio duenioExistente = findById(id);
        duenioExistente.setNombre(duenio.getNombre());
        duenioExistente.setApellido(duenio.getApellido());
        duenioExistente.setTelefono(duenio.getTelefono());
        duenioExistente.setCedula(duenio.getCedula());
        duenioExistente.setEmail(duenio.getEmail());
        return duenioRepository.save(duenioExistente);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        findById(id);
        duenioRepository.deleteById(id);
    }

    @Override
    public Duenio findById(Long id) {
        Duenio duenio = duenioRepository.findById(id);
        if (duenio == null) {
            throw new DuenioNotFoundException(id);
        }
        return duenio;
    }

    @Override
    public Duenio findByEmail(String email) {
        Duenio duenio = duenioRepository.findByEmail(email);
        if (duenio == null) {
            throw new DuenioNotFoundException("email", email);
        }
        return duenio;
    }

    @Override
    public Duenio findByNombre(String nombre) {
        Duenio duenio = duenioRepository.findByNombre(nombre);
        if (duenio == null) {
            throw new DuenioNotFoundException("nombre", nombre);
        }
        return duenio;
    }
}
