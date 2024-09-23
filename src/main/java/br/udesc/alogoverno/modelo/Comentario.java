package br.udesc.alogoverno.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "comentarios")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID uuid = UUID.randomUUID();

    private Date dataCriacao = new Date();

    @NotBlank
    private String descricao;

    @ManyToOne
    @Setter
    @JsonIgnore
    @JoinColumn(name="publicacao_id", nullable = false)
    private Publicacao publicacao;

    @ManyToOne
    @Setter
    @JoinColumn(name="autor_id", nullable = false)
    private Usuario autor;

    public Comentario() {
    }


}
