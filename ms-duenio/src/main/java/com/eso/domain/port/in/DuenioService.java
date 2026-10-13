package com.eso.domain.port.in;

import com.eso.adapter.in.web.dto.DuenioRequest;
import com.eso.adapter.in.web.dto.DuenioResponse;

import java.util.Collection;
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