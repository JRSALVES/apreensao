package com.apreensao.veiculo.controller;

import com.apreensao.veiculo.entity.Patio;
import com.apreensao.veiculo.service.PatioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patios")
public class PatioController {

    @Autowired
    private PatioService patioService;

    @GetMapping
    public List<Patio> getAllPatios() {
        return patioService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patio> getPatioById(@PathVariable Long id) {
        Optional<Patio> patio = patioService.findById(id);
        return patio.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Patio> createPatio(@RequestBody Patio patio) {
        Patio savedPatio = patioService.save(patio);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPatio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patio> updatePatio(@PathVariable Long id, @RequestBody Patio patio) {
        if (!patioService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        patio.setId(id);
        Patio updatedPatio = patioService.save(patio);
        return ResponseEntity.ok(updatedPatio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatio(@PathVariable Long id) {
        if (!patioService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        patioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
