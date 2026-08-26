package com.veterinariaEso.Repository;

import com.veterinariaEso.Model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {
    boolean existsByVeterinarioIdAndFechaAndHora(Long veterinarioId, LocalDate fecha, LocalTime hora);
    List<Turno> findByVeterinarioIdAndFecha(Long veterinarioId, LocalDate fecha);
    List<Turno> findByMascotaIdOrderByFechaDescHoraDesc(Long mascotaId, LocalDate fecha,  LocalTime hora);
}
