package com.veterinariaEso.Service;

import com.veterinariaEso.DTO.DuenioDTO;
import com.veterinariaEso.Exception.DuplicateResourceException;
import com.veterinariaEso.Exception.ResourceNotFoundException;
import com.veterinariaEso.Mapper.DuenioMapper;
import com.veterinariaEso.Model.Duenio;
import com.veterinariaEso.Repository.DuenioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DuenoServiceTest {

    @InjectMocks
    private DuenioService duenioService;
    @Mock
    private DuenioMapper duenioMapper;
    @Mock
    private DuenioRepository duenioRepository;

    //Test 1: Obtener todos los duenios
    @Test
    public void getAllDuenioListaVacia() {
        //DADO
        when(duenioRepository.findAll()).thenReturn(List.of());
        //CUANDO
        List<DuenioDTO> resultado = duenioService.getAllDuenios();
        //ENTONCES
        assertThat(resultado.isEmpty());
        verify(duenioRepository).findAll();
    }

    //Test 2: obtener todos los duenios
    @Test
    public void getAllDuenosListaLlena() {
        // dado
        Duenio duenio1 = new Duenio();
        Duenio duenio2 = new Duenio();
        when(duenioRepository.findAll()).thenReturn(List.of(duenio1, duenio2));
        // cuando
        List<DuenioDTO> resultado = duenioService.getAllDuenios();
        // entonces
        assertThat(resultado).isNotEmpty();
        assertThat(resultado).hasSize(2);
        verify(duenioRepository).findAll();
    }

    // Test 3: obtener duenio por id exitoso
    @Test
    public void getDuenioById_cuando_sea_exitosoDTO() {
        //DADO
        Duenio duenio = new Duenio();
        duenio.setId(1L);
        duenio.setNombre("Carlos");
        duenio.setApellido("Sanchez");
        duenio.setCedula("31541741");
        duenio.setTelefono(1230222);
        duenio.setEmail("carlossanchez@gmail.com");

        DuenioDTO duenioDTO = new DuenioDTO();
        duenioDTO.setId(duenio.getId());
        duenioDTO.setNombre("Carlos");
        //CUANDO
        when(duenioRepository.findById(1L)).thenReturn(Optional.of(duenio));
        when(duenioMapper.toDuenioDTO(duenio)).thenReturn(duenioDTO);
        //ENTONCES
        DuenioDTO resultado = duenioService.getDuenioById(1L);
        assertThat(resultado.getNombre()).isEqualTo("Carlos");
        assertThat(resultado.getId()).isEqualTo(1L);
    }

    //Test 4: obtener duenio por id no existoso
    @Test
    public void getDuenoByIdno_existosoDTO() {
        // DADO
        Long idNoExistente = 1L;
        // CUANDO
        when(duenioRepository.findById(idNoExistente)).thenReturn(Optional.empty());
        // ENTONCES
        assertThrows(ResourceNotFoundException.class, () -> {duenioService.getDuenioById(idNoExistente);});
        verify(duenioRepository).findById(idNoExistente);
    }

    //Test 5: crear un duenio exitoso
    @Test
    public void createDuenioExitoso() {
        //dado
        Duenio duenio = new Duenio();
        duenio.setId(1L);
        duenio.setNombre("Carlos");
        duenio.setApellido("Sanchez");
        duenio.setCedula("31541741");
        duenio.setTelefono(1230222);
        DuenioDTO duenioDTO = new DuenioDTO();
        duenioDTO.setId(1L);
        duenioDTO.setNombre("Carlos");
        duenioDTO.setApellido("Sanchez");
        duenioDTO.setCedula("31541741");
        duenioDTO.setTelefono(1230222);
        when(duenioMapper.toDuenio(any(DuenioDTO.class))).thenReturn(duenio);
        when(duenioRepository.save(any(Duenio.class))).thenReturn(duenio);
        when(duenioMapper.toDuenioDTO(any(Duenio.class))).thenReturn(duenioDTO);
        // CUANDO
        DuenioDTO resultado = duenioService.createDuenio(duenioDTO);
        // ENTONCES
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNombre()).isEqualTo("Carlos");
        verify(duenioRepository).save(any(Duenio.class));
    }

    //Test 6: crear duenio con cedula duplicada
    @Test
    public void createDuenio_cedulaDuplicada() {
        // DADO
        DuenioDTO duenioDTO = new DuenioDTO();
        duenioDTO.setNombre("Carlos");
        duenioDTO.setApellido("Sanchez");
        duenioDTO.setCedula("31541741");
        duenioDTO.setTelefono(1230222);
        when(duenioRepository.existsByCedula("31541741")).thenReturn(true);
        // CUANDO y ENTONCES
        assertThrows(DuplicateResourceException.class, () -> {duenioService.createDuenio(duenioDTO);});
        verify(duenioRepository, never()).save(any(Duenio.class));
    }
}