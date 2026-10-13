package com.eso.adapter.in.web.dto;

import com.eso.domain.model.Mascota;

public final class MascotaMapper {

    private MascotaMapper() {}

    public static Mascota toEntity(MascotaRequest mascotaRequest) {
        if (mascotaRequest == null) {
            return null;
        }

        Mascota mascota = new Mascota();
        mascota.setNombre(mascotaRequest.getNombre());
        mascota.setEspecie(mascotaRequest.getEspecie());
        mascota.setRaza(mascotaRequest.getRaza());
        mascota.setFechaNacimiento(mascotaRequest.getFechaNacimiento());
        mascota.setDuenioId(mascotaRequest.getDuenioId());

        return mascota;
    }

    public static MascotaResponse toResponse(Mascota mascota) {
        if (mascota == null) {
            return null;
        }

        return new MascotaResponse(
                mascota.getId(),
                mascota.getNombre(),
                mascota.getEspecie(),
                mascota.getRaza(),
                mascota.getFechaNacimiento(),
                mascota.getDuenioId()
        );
    }
}