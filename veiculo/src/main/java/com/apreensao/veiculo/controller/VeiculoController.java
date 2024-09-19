package com.apreensao.veiculo.controller;


import com.apreensao.veiculo.entity.Veiculo;
import com.apreensao.veiculo.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @GetMapping
    public List<Veiculo> getAllVeiculos() {
        return veiculoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Veiculo> getVeiculoById(@PathVariable Long id) {
        Optional<Veiculo> veiculo = veiculoService.findById(id);
        return veiculo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Veiculo> createVeiculo(@RequestBody Veiculo veiculo) {
        Veiculo savedVeiculo = veiculoService.save(veiculo);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVeiculo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Veiculo> updateVeiculo(@PathVariable Long id, @RequestBody Veiculo veiculo) {
        if (!veiculoService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        veiculo.setId(id);
        Veiculo updatedVeiculo = veiculoService.save(veiculo);
        return ResponseEntity.ok(updatedVeiculo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVeiculo(@PathVariable Long id) {
        if (!veiculoService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        veiculoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
