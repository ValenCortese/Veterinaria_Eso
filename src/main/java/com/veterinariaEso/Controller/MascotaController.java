package com.veterinariaEso.Controller;

import com.veterinariaEso.DTO.MascotaDTO;
import com.veterinariaEso.Exception.ResourceNotFoundException;
import com.veterinariaEso.Service.MascotaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MascotaController {

    @Autowired
    private final MascotaService mascotaService;

    @GetMapping
    public ResponseEntity<List<MascotaDTO>> getAllMascotas() {
        return ResponseEntity.ok(mascotaService.getAllMascotas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMascotaById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(mascotaService.getMascotaById(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createMascota(@RequestParam Long duenioId, @RequestBody MascotaDTO mascotaDTO) {
        try {
            MascotaDTO nueva = mascotaService.createMascota(duenioId, mascotaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMascota(@PathVariable Long id, @RequestBody MascotaDTO MascotaDTO) {
        try {
            return ResponseEntity.ok(mascotaService.updateMascota(id, MascotaDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMascota(@PathVariable Long id) {
        try {
            mascotaService.deleteMascota(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
