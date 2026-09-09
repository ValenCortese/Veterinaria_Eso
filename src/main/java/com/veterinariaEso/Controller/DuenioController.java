package com.veterinariaEso.Controller;

import com.veterinariaEso.DTO.DuenioDTO;
import com.veterinariaEso.Exception.ResourceNotFoundException;
import com.veterinariaEso.Service.DuenioService;
import com.veterinariaEso.Service.MascotaService;
import jakarta.validation.Valid;
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
        return ResponseEntity.ok(duenioService.getDuenioById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<DuenioDTO> getDuenioByEmail(@PathVariable String email) {
        return ResponseEntity.ok(duenioService.getDuenioByEmail(email));
    }

    @GetMapping("{id}/mascotas")
    public ResponseEntity<?> getMascotasByDuenio(@PathVariable Long id){
        return ResponseEntity.ok(mascotaService.getMascotaByDuenio(id));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<DuenioDTO> getDuenioByNombre(@RequestParam String nombre){
        return ResponseEntity.ok(duenioService.getDuenioByNombre(nombre));
    }

    @PostMapping
    public ResponseEntity<?> createDuenio(@Valid @RequestBody DuenioDTO duenioDTO) {
        DuenioDTO nuevo = duenioService.createDuenio(duenioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDuenio(@PathVariable Long id, @Valid @RequestBody DuenioDTO duenioDTO) {
        return ResponseEntity.ok(duenioService.updateDuenio(id, duenioDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDuenio(@PathVariable Long id) {
        duenioService.deleteDuenio(id);
        return ResponseEntity.noContent().build();
    }
}
