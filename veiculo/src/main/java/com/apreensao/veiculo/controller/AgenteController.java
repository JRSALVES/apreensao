package com.apreensao.veiculo.controller;


import com.apreensao.veiculo.entity.Agente;
import com.apreensao.veiculo.service.AgenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/agentes")
public class AgenteController {

    @Autowired
    private AgenteService agenteService;

    @GetMapping
    public List<Agente> getAllAgentes() {
        return agenteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agente> getAgenteById(@PathVariable Long id) {
        Optional<Agente> agente = agenteService.findById(id);
        return agente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Agente> createAgente(@RequestBody Agente agente) {
        Agente savedAgente = agenteService.save(agente);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAgente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agente> updateAgente(@PathVariable Long id, @RequestBody Agente agente) {
        if (!agenteService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        agente.setId(id);
        Agente updatedAgente = agenteService.save(agente);
        return ResponseEntity.ok(updatedAgente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgente(@PathVariable Long id) {
        if (!agenteService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        agenteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
