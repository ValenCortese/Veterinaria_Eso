package com.veterinariaEso.Controller;

import com.veterinariaEso.DTO.DuenioDTO;
import com.veterinariaEso.Exception.ResourceNotFoundException;
import com.veterinariaEso.Service.DuenioService;
import com.veterinariaEso.Service.MascotaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/duenios")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DuenioController {
    // debemos devolver dto no duenio a partir de ahora
    @Autowired
    private final DuenioService duenioService;
    @Autowired
    private final MascotaService mascotaService;

    @GetMapping
    public ResponseEntity<List<DuenioDTO>> getAllDuenios() {
        return ResponseEntity.ok(duenioService.getAllDuenios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDuenioById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(duenioService.getDuenioById(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<DuenioDTO> getDuenioByEmail(@PathVariable String email) {
        try {
            return ResponseEntity.ok(duenioService.getDuenioByEmail(email));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}/mascotas")
    public ResponseEntity<?> getMascotasByDuenio(@PathVariable Long id){
        try {
            return ResponseEntity.ok(mascotaService.getMascotaByDuenio(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<DuenioDTO> getDuenioByNombre(@RequestParam String nombre){
        try {
            return ResponseEntity.ok(duenioService.getDuenioByNombre(nombre));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createDuenio(@RequestBody DuenioDTO duenioDTO) {
        try {
            DuenioDTO nuevo = duenioService.createDuenio(duenioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDuenio(@PathVariable Long id, @RequestBody DuenioDTO duenioDTO) {
        try {
            return ResponseEntity.ok(duenioService.updateDuenio(id, duenioDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDuenio(@PathVariable Long id) {
        try {
            duenioService.deleteDuenio(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
