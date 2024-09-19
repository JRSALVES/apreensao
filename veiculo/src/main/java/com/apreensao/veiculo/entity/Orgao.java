package com.apreensao.veiculo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "orgao")
public class Orgao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private String telefone;
}