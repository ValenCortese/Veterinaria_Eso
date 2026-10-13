package com.eso.domain.port.in;

import com.eso.application.dto.DuenioRequest;
import com.eso.application.dto.DuenioResponse;
import java.util.List;

public interface DuenioService {

    List<DuenioResponse> listAll();
    DuenioResponse save(DuenioRequest duenioRequest);
    DuenioResponse update(Long id, DuenioRequest duenioRequest);
    void deleteById(Long id);
    DuenioResponse findById(Long id);
    DuenioResponse findByEmail(String email);
    DuenioResponse findByNombre(String nombre);
}