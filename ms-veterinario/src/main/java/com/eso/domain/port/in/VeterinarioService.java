package com.eso.domain.port.in;

import com.eso.adapter.in.web.dto.VeterinarioRequest;
import com.eso.adapter.in.web.dto.VeterinarioResponse;
import java.util.List;

public interface VeterinarioService {
    List<VeterinarioResponse> findAll();
    VeterinarioResponse findById(Long id);
    List<VeterinarioResponse> findByEspecialidad(String especialidad);
    VeterinarioResponse save(VeterinarioRequest request);
    VeterinarioResponse update(Long id, VeterinarioRequest request);
    void deleteById(Long id);
}