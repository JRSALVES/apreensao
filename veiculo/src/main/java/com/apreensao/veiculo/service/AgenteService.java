package com.apreensao.veiculo.service;

import com.apreensao.veiculo.entity.Agente;
import com.apreensao.veiculo.repository.AgenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgenteService{

    @Autowired
    private AgenteRepository agenteRepository;

    public List<Agente> findAll() {
        return agenteRepository.findAll();
    }

    public Optional<Agente> findById(Long id) {
        return agenteRepository.findById(id);
    }

    public Agente save(Agente agente) {
        return agenteRepository.save(agente);
    }

    public void deleteById(Long id) {
        agenteRepository.deleteById(id);
    }
}