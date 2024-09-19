package com.apreensao.veiculo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "agente")
public class Agente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String matricula;

    @Column(nullable = false)
    private String cargo;
}