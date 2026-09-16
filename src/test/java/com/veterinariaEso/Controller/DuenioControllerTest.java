package com.veterinariaEso.Controller;

import com.veterinariaEso.DTO.DuenioDTO;
import com.veterinariaEso.Exception.ResourceNotFoundException;
import com.veterinariaEso.Service.DuenioService;
import com.veterinariaEso.Service.MascotaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import tools.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(DuenioController.class)
public class DuenioControllerTest {

    @MockitoBean
    private DuenioService duenioService;

    @MockitoBean
    private MascotaService mascotaService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private DuenioDTO createDuenioDTO() {
        DuenioDTO duenioDTO = new DuenioDTO();
        duenioDTO.setId(1L);
        duenioDTO.setNombre("Duenio 1");
        duenioDTO.setApellido("Apellido 1");
        duenioDTO.setCedula("1234567");
        duenioDTO.setEmail("duenio1@vet.com");
        duenioDTO.setTelefono(12345678);
        return duenioDTO;
    }

    // Test: GET /api/duenios -> HTTP 200 con lista vacía
    @Test
    void getDuenos_RetornaListaVacia_Http200() throws Exception {
        when(duenioService.getAllDuenios()).thenReturn(List.of());
        mockMvc.perform(get("/api/duenios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
    }

    // Test: GET /api/duenios/1 -> HTTP 200 cuando el service retorna un dueño
    @Test
    void getDuenoById_Existente_Http200() throws Exception {
        when(duenioService.getDuenioById(1L)).thenReturn(createDuenioDTO());
        mockMvc.perform(get("/api/duenios/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Duenio 1"));
    }

    // Test: GET /api/duenios/99 -> HTTP 404 cuando el service lanza ResourceNotFoundException
    @Test
    void getDuenoById_NoExistente_Http404() throws Exception {
        when(duenioService.getDuenioById(99L)).thenThrow(new ResourceNotFoundException("Dueño no encontrado", 99L));
        mockMvc.perform(get("/api/duenios/99")).andExpect(status().isNotFound());
    }

    // Test: POST /api/duenios con body válido -> HTTP 201
    @Test
    void createDueno_BodyValido_Http201() throws Exception {
        DuenioDTO inputDTO = createDuenioDTO();
        DuenioDTO mockDuenoGuardado = createDuenioDTO();
        mockDuenoGuardado.setId(1L);
        when(duenioService.createDuenio(any())).thenReturn(mockDuenoGuardado);
        String jsonValido = objectMapper.writeValueAsString(inputDTO);
        mockMvc.perform(post("/api/duenios").contentType(MediaType.APPLICATION_JSON).content(jsonValido))
                .andExpect(status().isCreated());
    }

    // Test: POST /api/duenios con body inválido (email vacío) -> HTTP 400
    @Test
    void createDueno_BodyInvalido_EmailVacio_Http400() throws Exception {
        String jsonInvalido = """
                {
                    "nombre": "Carlos",
                    "apellido": "Sanchez",
                    "email": "",
                    "cedula": "12345678"
                }
                """;
        mockMvc.perform(post("/api/duenios").contentType(MediaType.APPLICATION_JSON).content(jsonInvalido))
                .andExpect(status().isBadRequest());
    }
}
