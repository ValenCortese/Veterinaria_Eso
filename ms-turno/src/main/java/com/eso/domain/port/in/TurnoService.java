package com.eso.domain.port.in;

import com.eso.adapter.in.web.dto.TurnoRequest;
import com.eso.adapter.in.web.dto.TurnoResponse;
import java.util.List;

public interface TurnoService {
    List<TurnoResponse> findAll();
    TurnoResponse findById(Long id);
    List<TurnoResponse> findByVeterinarioId(Long veterinarioId);
    List<TurnoResponse> findByMascotaId(Long mascotaId);
    TurnoResponse save(TurnoRequest request);
    TurnoResponse cancelar(Long id);
}