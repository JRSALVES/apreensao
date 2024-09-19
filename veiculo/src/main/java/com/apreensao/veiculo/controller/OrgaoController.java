package com.apreensao.veiculo.controller;

import com.apreensao.veiculo.entity.Orgao;
import com.apreensao.veiculo.service.OrgaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orgaos")
public class OrgaoController {

    @Autowired
    private OrgaoService orgaoService;

    @GetMapping
    public List<Orgao> getAllOrgaos() {
        return orgaoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orgao> getOrgaoById(@PathVariable Long id) {
        Optional<Orgao> orgao = orgaoService.findById(id);
        return orgao.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Orgao> createOrgao(@RequestBody Orgao orgao) {
        Orgao savedOrgao = orgaoService.save(orgao);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrgao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Orgao> updateOrgao(@PathVariable Long id, @RequestBody Orgao orgao) {
        if (!orgaoService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        orgao.setId(id);
        Orgao updatedOrgao = orgaoService.save(orgao);
        return ResponseEntity.ok(updatedOrgao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrgao(@PathVariable Long id) {
        if (!orgaoService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        orgaoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
