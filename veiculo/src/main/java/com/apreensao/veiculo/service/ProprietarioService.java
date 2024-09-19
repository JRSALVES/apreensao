package com.apreensao.veiculo.service;


import com.apreensao.veiculo.entity.Proprietario;
import com.apreensao.veiculo.repository.ProprietarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProprietarioService {

    @Autowired
    private ProprietarioRepository proprietarioRepository;

    public List<Proprietario> findAll() {
        return proprietarioRepository.findAll();
    }

    public Optional<Proprietario> findById(Long id) {
        return proprietarioRepository.findById(id);
    }

    public Proprietario save(Proprietario proprietario) {
        return proprietarioRepository.save(proprietario);
    }

    public void deleteById(Long id) {
        proprietarioRepository.deleteById(id);
    }
}
