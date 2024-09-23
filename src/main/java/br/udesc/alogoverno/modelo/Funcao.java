package br.udesc.alogoverno.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "funcoes")
public class Funcao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EnumFuncoes nome;

    public Funcao(EnumFuncoes nome) {
        this.nome = nome;
    }

    public Funcao() {
    }

}


