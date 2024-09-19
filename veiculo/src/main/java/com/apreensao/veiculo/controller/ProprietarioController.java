package com.apreensao.veiculo.controller;

import com.apreensao.veiculo.entity.Proprietario;
import com.apreensao.veiculo.service.ProprietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/proprietarios")
public class ProprietarioController {

    @Autowired
    private ProprietarioService proprietarioService;

    @GetMapping
    public List<Proprietario> getAllProprietarios() {
        return proprietarioService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proprietario> getProprietarioById(@PathVariable Long id) {
        Optional<Proprietario> proprietario = proprietarioService.findById(id);
        return proprietario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Proprietario> createProprietario(@RequestBody Proprietario proprietario) {
        Proprietario savedProprietario = proprietarioService.save(proprietario);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProprietario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proprietario> updateProprietario(@PathVariable Long id, @RequestBody Proprietario proprietario) {
        if (!proprietarioService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        proprietario.setId(id);  // Atualiza o ID do proprietario para garantir que o mesmo é usado no banco de dados
        Proprietario updatedProprietario = proprietarioService.save(proprietario);
        return ResponseEntity.ok(updatedProprietario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProprietario(@PathVariable Long id) {
        if (!proprietarioService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        proprietarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
