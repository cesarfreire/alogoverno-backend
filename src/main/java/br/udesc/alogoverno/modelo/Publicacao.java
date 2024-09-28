package br.udesc.alogoverno.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "publicacoes",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "titulo"),
        })
public class Publicacao {
    private UUID uuid = UUID.randomUUID();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dataCriacao = new Date();

    @NotBlank
    @Setter
    @Size(max = 150)
    private String titulo;

    @NotBlank
    @Setter
    private String descricao;

    @ManyToOne
    @Setter
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    @OneToMany(mappedBy = "publicacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios;

    @Setter
    @ElementCollection
    @CollectionTable(name = "publicacao_midias", joinColumns = @JoinColumn(name = "publicacao_id"))
    @Column(name = "url_midia")
    private List<String> midias;

    @OneToMany(mappedBy = "publicacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RespostaOficial> respostasOficiais;


    public Publicacao(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
    }
}
