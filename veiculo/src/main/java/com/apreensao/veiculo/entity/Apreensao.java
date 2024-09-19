package com.apreensao.veiculo.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "apreensao")
public class Apreensao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;

    @ManyToOne
    @JoinColumn(name = "agente_id", nullable = false)
    private Agente agente;

    @ManyToOne
    @JoinColumn(name = "orgao_id", nullable = false)
    private Orgao orgao;

    @ManyToOne
    @JoinColumn(name = "patio_id", nullable = false)
    private Patio patio;

    @Column(nullable = false)
    private LocalDateTime dataApreensao;

    @Column(nullable = false)
    private String motivo;

    @Column
    private String observacoes;
}
