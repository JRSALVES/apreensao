package com.apreensao.veiculo.repository;

import com.apreensao.veiculo.entity.Proprietario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProprietarioRepository extends JpaRepository<Proprietario, Long> {
    boolean existsByCpf(String cpf);
}