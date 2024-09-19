package com.apreensao.veiculo.repository;

import com.apreensao.veiculo.entity.Orgao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrgaoRepository extends JpaRepository<Orgao, Long> {
}