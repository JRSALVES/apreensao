package com.apreensao.veiculo.controller;

import com.apreensao.veiculo.entity.*;
import com.apreensao.veiculo.service.ApreensaoService;
import com.apreensao.veiculo.service.VeiculoService;
import com.apreensao.veiculo.service.AgenteService;
import com.apreensao.veiculo.service.OrgaoService;
import com.apreensao.veiculo.service.PatioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/apreensoes")
public class ApreensaoController {

    @Autowired
    private ApreensaoService apreensaoService;

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private AgenteService agenteService;

    @Autowired
    private OrgaoService orgaoService;

    @Autowired
    private PatioService patioService;

    @PostMapping
    public ResponseEntity<?> createApreensao(@RequestBody Apreensao apreensao) {
        try {
            Optional<Veiculo> veiculoOpt = veiculoService.findById(apreensao.getVeiculo().getId());
            if (!veiculoOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Veículo com ID " + apreensao.getVeiculo().getId() + " não encontrado.");
            }

            Optional<Agente> agenteOpt = agenteService.findById(apreensao.getAgente().getId());
            if (!agenteOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Agente com ID " + apreensao.getAgente().getId() + " não encontrado.");
            }

            Optional<Orgao> orgaoOpt = orgaoService.findById(apreensao.getOrgao().getId());
            if (!orgaoOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Órgão com ID " + apreensao.getOrgao().getId() + " não encontrado.");
            }

            Optional<Patio> patioOpt = patioService.findById(apreensao.getPatio().getId());
            if (!patioOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Pátio com ID " + apreensao.getPatio().getId() + " não encontrado.");
            }

            // Setando entidades relacionadas
            apreensao.setVeiculo(veiculoOpt.get());
            apreensao.setAgente(agenteOpt.get());
            apreensao.setOrgao(orgaoOpt.get());
            apreensao.setPatio(patioOpt.get());

            Apreensao novaApreensao = apreensaoService.save(apreensao);
            return new ResponseEntity<>(novaApreensao, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // READ - Buscar todas as apreensões
    @GetMapping
    public ResponseEntity<List<Apreensao>> getAllApreensoes() {
        List<Apreensao> apreensoes = apreensaoService.findAll();
        return new ResponseEntity<>(apreensoes, HttpStatus.OK);
    }

    // READ - Buscar uma apreensão por ID
    @GetMapping("/{id}")
    public ResponseEntity<Apreensao> getApreensaoById(@PathVariable Long id) {
        Optional<Apreensao> apreensao = apreensaoService.findById(id);
        if (apreensao.isPresent()) {
            return new ResponseEntity<>(apreensao.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // UPDATE - Atualizar uma apreensão por ID
    @PutMapping("/{id}")
    public ResponseEntity<Apreensao> updateApreensao(@PathVariable Long id, @RequestBody Apreensao apreensaoDetails) {
        Optional<Apreensao> apreensao = apreensaoService.findById(id);
        if (apreensao.isPresent()) {
            Apreensao apreensaoAtualizada = apreensao.get();
            apreensaoAtualizada.setVeiculo(apreensaoDetails.getVeiculo());
            apreensaoAtualizada.setAgente(apreensaoDetails.getAgente());
            apreensaoAtualizada.setOrgao(apreensaoDetails.getOrgao());
            apreensaoAtualizada.setPatio(apreensaoDetails.getPatio());
            apreensaoAtualizada.setDataApreensao(apreensaoDetails.getDataApreensao());
            apreensaoAtualizada.setMotivo(apreensaoDetails.getMotivo());
            apreensaoAtualizada.setObservacoes(apreensaoDetails.getObservacoes());

            apreensaoService.save(apreensaoAtualizada);
            return new ResponseEntity<>(apreensaoAtualizada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE - Excluir uma apreensão por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteApreensao(@PathVariable Long id) {
        Optional<Apreensao> apreensao = apreensaoService.findById(id);
        if (apreensao.isPresent()) {
            apreensaoService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
