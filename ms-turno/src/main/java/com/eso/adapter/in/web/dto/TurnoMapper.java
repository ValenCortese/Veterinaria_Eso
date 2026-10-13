package com.eso.adapter.in.web.dto;

import com.eso.domain.model.EstadoTurno;
import com.eso.domain.model.Turno;

public final class TurnoMapper {
    private TurnoMapper() {}

    public static Turno toEntity(TurnoRequest request) {
        if (request == null) return null;
        Turno turno = new Turno();
        turno.setFechaHora(request.getFechaHora());
        turno.setMascotaId(request.getMascotaId());
        turno.setVeterinarioId(request.getVeterinarioId());
        turno.setMotivo(request.getMotivo());
        turno.setEstado(EstadoTurno.PENDIENTE);
        return turno;
    }

    public static TurnoResponse toResponse(Turno turno) {
        if (turno == null) return null;
        return new TurnoResponse(
                turno.getId(),
                turno.getFechaHora(),
                turno.getMascotaId(),
                turno.getVeterinarioId(),
                turno.getMotivo(),
                turno.getEstado()
        );
    }
}