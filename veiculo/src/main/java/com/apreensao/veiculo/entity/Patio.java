package com.apreensao.veiculo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "patio")
public class Patio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private Integer capacidade;
}