package com.veterinariaEso.Controller;

import com.veterinariaEso.DTO.VeterinarioDTO;
import com.veterinariaEso.Exception.ResourceNotFoundException;
import com.veterinariaEso.Service.VeterinarioService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/veterinarios")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VeterinarioController {

    @Autowired
    private VeterinarioService veterinarioService;

    @GetMapping
    public ResponseEntity<List<VeterinarioDTO>> getAllVeterinarios() {
        return ResponseEntity.ok(veterinarioService.getAllVeterinarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVeterinarioById(@PathVariable Long id) {
        return ResponseEntity.ok(veterinarioService.getVeterinarioById(id));
    }

    @PostMapping
    public ResponseEntity<?> createVeterinario(@Valid @RequestBody VeterinarioDTO veterinarioDTO) {
        VeterinarioDTO nuevo = veterinarioService.createVeterinario(veterinarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVeterinario(@PathVariable Long id, @Valid @RequestBody VeterinarioDTO veterinarioDTO) {
        return ResponseEntity.ok(veterinarioService.updateVeterinario(id, veterinarioDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVeterinario(@PathVariable Long id) {
        veterinarioService.deleteVeterinario(id);
        return ResponseEntity.noContent().build();
    }
}
