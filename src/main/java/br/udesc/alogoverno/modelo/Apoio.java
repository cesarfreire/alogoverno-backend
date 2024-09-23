package br.udesc.alogoverno.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "apoios", uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id", "publicacao_id"})})
@Getter
@Setter
public class Apoio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID uuid = UUID.randomUUID();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "publicacao_id", nullable = false)
    private Publicacao publicacao;

    // Construtor padrão
    public Apoio() {}

    // Construtor com parâmetros
    public Apoio(Usuario usuario, Publicacao publicacao) {
        this.usuario = usuario;
        this.publicacao = publicacao;
    }
}
