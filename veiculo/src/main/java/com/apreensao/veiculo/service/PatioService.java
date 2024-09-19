package com.apreensao.veiculo.service;

import com.apreensao.veiculo.entity.Patio;
import com.apreensao.veiculo.repository.PatioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatioService {

    @Autowired
    private PatioRepository patioRepository;

    public List<Patio> findAll() {
        return patioRepository.findAll();
    }

    public Optional<Patio> findById(Long id) {
        return patioRepository.findById(id);
    }

    public Patio save(Patio patio) {
        return patioRepository.save(patio);
    }

    public void deleteById(Long id) {
        patioRepository.deleteById(id);
    }
}
