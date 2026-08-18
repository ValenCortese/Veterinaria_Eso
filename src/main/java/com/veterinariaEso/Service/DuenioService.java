package com.veterinariaEso.Service;

import com.veterinariaEso.Exception.ResourceNotFoundException;
import com.veterinariaEso.Model.Duenio;
import com.veterinariaEso.Repository.DuenioRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DuenioService {

    private final DuenioRepository duenioRepository;

    public List<Duenio> getAllDuenios() {
        return duenioRepository.findAll();
    }

    public Duenio getDuenioById(Long id) {
        return duenioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Duenio", id));
    }

    public Duenio getDuenioByEmail(String email) {
        return duenioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No se encontró un dueño con el email: " + email));
    }

    @Transactional
    public Duenio createDuenio(Duenio duenio) {
        if (duenioRepository.existsByCedula(duenio.getCedula())) {
            throw new RuntimeException("Ya existe un dueño con Cedula: " +
                    duenio.getCedula());
        }
        return duenioRepository.save(duenio);
    }

    public Duenio updateDuenio(Long id, Duenio duenioActualizado) {
        Duenio duenio = getDuenioById(id);
        duenio.setNombre(duenioActualizado.getNombre());
        duenio.setApellido(duenioActualizado.getApellido());
        duenio.setTelefono(duenioActualizado.getTelefono());
        duenio.setEmail(duenioActualizado.getEmail());
        return duenioRepository.save(duenio);
    }

    public void deleteDuenio(Long id) {
        Duenio duenio = getDuenioById(id);
        duenioRepository.delete(duenio);
    }
}