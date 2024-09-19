package com.apreensao.veiculo.service;

import com.apreensao.veiculo.entity.Apreensao;
import com.apreensao.veiculo.entity.Veiculo;
import com.apreensao.veiculo.entity.Proprietario;
import com.apreensao.veiculo.repository.ApreensaoRepository;
import com.apreensao.veiculo.repository.VeiculoRepository;
import com.apreensao.veiculo.repository.ProprietarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApreensaoService {

    @Autowired
    private ApreensaoRepository apreensaoRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private ProprietarioRepository proprietarioRepository;

    public List<Apreensao> findAll() {
        return apreensaoRepository.findAll();
    }

    public Optional<Apreensao> findById(Long id) {
        return apreensaoRepository.findById(id);
    }

    public Apreensao save(Apreensao apreensao) {
        if (veiculoRepository.existsByPlaca(apreensao.getVeiculo().getPlaca())) {
            throw new IllegalArgumentException("Placa do veículo já cadastrada.");
        }
        if (proprietarioRepository.existsByCpf(apreensao.getVeiculo().getProprietario().getCpf())) {
            throw new IllegalArgumentException("CPF do proprietário já cadastrado.");
        }
        if (apreensaoRepository.existsByVeiculo(apreensao.getVeiculo())) {
            throw new IllegalArgumentException("Veículo já está apreendido.");
        }

        return apreensaoRepository.save(apreensao);
    }


    public void deleteById(Long id) {
        apreensaoRepository.deleteById(id);
    }
}
