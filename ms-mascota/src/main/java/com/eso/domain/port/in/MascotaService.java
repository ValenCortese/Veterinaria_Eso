package com.eso.domain.port.in;

import com.eso.adapter.in.web.dto.MascotaRequest;
import com.eso.adapter.in.web.dto.MascotaResponse;

import java.util.List;

public interface MascotaService {

    List<MascotaResponse> getAllMascotas();
    MascotaResponse getMascotaById(Long id);
    MascotaResponse save(MascotaRequest mascotaRequest);
    MascotaResponse update(Long id, MascotaRequest mascotaRequest);
    void deleteMascota(Long id);
    List<MascotaResponse> findByDuenioId(Long duenioId);
}