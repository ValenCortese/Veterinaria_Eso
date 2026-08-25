package com.veterinariaEso.Service;

import com.veterinariaEso.Exception.ResourceNotFoundException;
import com.veterinariaEso.Model.Duenio;
import com.veterinariaEso.Model.Mascota;
import com.veterinariaEso.Repository.DuenioRepository;
import com.veterinariaEso.Repository.MascotaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequiredArgsConstructor
public class MascotaService {

    private final MascotaRepository mascotaRepository;
    private final DuenioRepository duenioRepository;

    public List<Mascota> getAllMascotas() {
        return mascotaRepository.findAll();
    }

    public Mascota getMascotaById(Long id) {
        return mascotaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Mascota", id));
    }

    @Transactional
    public List<Mascota> getMascotaByDuenio(Long duenioId) {
        if (!duenioRepository.existsById(duenioId)) {
            throw new ResourceNotFoundException("Duenio", duenioId);
        }
        return mascotaRepository.findByDuenioId(duenioId);
    }

    @Transactional
    public Mascota createMascota(Long duenioId,Mascota mascota) {
        Duenio duenio = duenioRepository.findById(duenioId).orElseThrow(() -> new ResourceNotFoundException("Duenio", duenioId));
        mascota.setDuenio(duenio);
        return mascotaRepository.save(mascota);
    }

    @Transactional
    public Mascota updateMascota(Long id, Mascota mascotaActualizada) {
        Mascota mascota = getMascotaById(id);
        mascota.setNombre(mascotaActualizada.getNombre());
        mascota.setEspecie(mascotaActualizada.getEspecie());
        mascota.setRaza(mascotaActualizada.getRaza());
        mascota.setFechaNacimiento(mascotaActualizada.getFechaNacimiento());
        return mascotaRepository.save(mascota);
    }

    public void deleteMascota(Long id) {
        Mascota mascota = getMascotaById(id);
        mascotaRepository.delete(mascota);
    }
}
