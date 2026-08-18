package com.veterinariaEso.Repository;

import com.veterinariaEso.Model.Duenio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DuenioRepository extends JpaRepository<Duenio, Long> {

    Optional<Duenio> findByEmail(String email);
    boolean existsByCedula(String cedula);
}

