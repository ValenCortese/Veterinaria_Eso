package com.eso.domain.port.in;

import com.eso.domain.model.Duenio;

import java.util.List;

public interface DuenioService {

    List<Duenio> listAll();
    Duenio save(Duenio duenio);
    Duenio update(Long id, Duenio duenio);
    void deleteById(Long id);
    Duenio findById(Long id);
    Duenio findByEmail(String email);
    Duenio findByNombre(String nombre);
}