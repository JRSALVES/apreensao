package com.apreensao.veiculo.repository;

import com.apreensao.veiculo.entity.Apreensao;
import com.apreensao.veiculo.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApreensaoRepository extends JpaRepository<Apreensao, Long> {

    // Método para verificar se já existe uma apreensão para um veículo
    boolean existsByVeiculo(Veiculo veiculo);
}
