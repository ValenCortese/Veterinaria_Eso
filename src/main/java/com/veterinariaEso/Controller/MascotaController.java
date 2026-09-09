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
import jakarta.validation.Valid;

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
        return ResponseEntity.ok(mascotaService.getMascotaById(id));
    }

    @PostMapping
    public ResponseEntity<?> createMascota(@RequestParam Long duenioId, @Valid @RequestBody MascotaDTO mascotaDTO) {
        MascotaDTO nueva = mascotaService.createMascota(duenioId, mascotaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMascota(@PathVariable Long id, @Valid @RequestBody MascotaDTO MascotaDTO) {
        return ResponseEntity.ok(mascotaService.updateMascota(id, MascotaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMascota(@PathVariable Long id) {
        mascotaService.deleteMascota(id);
        return ResponseEntity.noContent().build();
    }
}
