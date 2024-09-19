package com.apreensao.veiculo.entity;

import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "veiculo")  // Define o nome da tabela, opcional
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "placa", unique = true, nullable = false)
    private String placa;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private String fabricante;

    @Column(nullable = false)
    private Integer ano;

    @Column(nullable = false)
    private String cor;

    @ManyToOne
    @JoinColumn(name = "proprietario_id", nullable = false)
    private Proprietario proprietario;

}
