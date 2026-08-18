package com.veterinariaEso.Controller;

import com.veterinariaEso.Exception.ResourceNotFoundException;
import com.veterinariaEso.Model.Duenio;
import com.veterinariaEso.Service.DuenioService;
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

    @Autowired
    private final DuenioService duenioService;

    @GetMapping
    public ResponseEntity<List<Duenio>> getAllDuenios() {
        return ResponseEntity.ok(duenioService.getAllDuenios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Duenio> getDuenioById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(duenioService.getDuenioById(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{email]")
    public ResponseEntity<Duenio> getDuenioByEmail(@PathVariable String email) {
        try {
            return ResponseEntity.ok(duenioService.getDuenioByEmail(email));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createDuenio(@RequestBody Duenio duenio) {
        try {
            Duenio nuevo = duenioService.createDuenio(duenio);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDuenio(@PathVariable Long id, @RequestBody Duenio duenio) {
        try {
            return ResponseEntity.ok(duenioService.updateDuenio(id, duenio));
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
