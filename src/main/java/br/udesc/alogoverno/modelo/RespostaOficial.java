package br.udesc.alogoverno.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "respostas_oficiais")
public class RespostaOficial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID uuid = UUID.randomUUID();

    private Date dataCriacao = new Date();

    @ManyToOne
    @JoinColumn(name = "publicacao_id", nullable = false)
    private Publicacao publicacao;

    @NotBlank
    @Column(columnDefinition = "TEXT") // Define o campo como texto longo
    private String texto;


}
