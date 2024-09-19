package com.apreensao.veiculo.service;

import com.apreensao.veiculo.entity.Orgao;
import com.apreensao.veiculo.repository.OrgaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrgaoService {

    @Autowired
    private OrgaoRepository orgaoRepository;


    public List<Orgao> findAll() {
        return orgaoRepository.findAll();
    }


    public Optional<Orgao> findById(Long id) {
        return orgaoRepository.findById(id);
    }


    public Orgao save(Orgao orgao) {
        return orgaoRepository.save(orgao);
    }


    public void deleteById(Long id) {
        orgaoRepository.deleteById(id);
    }
}