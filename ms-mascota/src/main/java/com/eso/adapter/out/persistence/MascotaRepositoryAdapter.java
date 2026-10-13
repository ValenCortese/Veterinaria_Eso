package com.eso.adapter.out.persistence;

import com.eso.domain.model.Mascota;
import com.eso.domain.port.out.MascotaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MascotaRepositoryAdapter implements MascotaRepositoryPort {

    private final MascotaJpaRepository jpaRepository;

    @Override
    public List<Mascota> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Mascota findById(Long id) {
        return jpaRepository.findById(id).orElse(null);
    }

    @Override
    public Mascota save(Mascota mascota) {
        return jpaRepository.save(mascota);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Mascota findByNombre(String nombre) {
        return jpaRepository.findByNombre(nombre).orElse(null);
    }

    @Override
    public List<Mascota> findByDuenioId(Long duenioId) {
        return jpaRepository.findByDuenioId(duenioId);
    }

    @Override
    public boolean existsByNombreAndDuenioId(String nombre, Long duenioId) {
        return jpaRepository.existsByNombreAndDuenioId(nombre, duenioId);
    }
}