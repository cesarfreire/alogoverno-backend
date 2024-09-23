package br.udesc.alogoverno.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Table(name = "usuarios",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "cpf")
        })
public class Usuario {

    public Usuario(){}

    public Usuario(
            String primeiroNome,
            String ultimoNome,
            String email,
            String senha,
            String cpf
    ){
        this.primeiroNome = primeiroNome;
        this.ultimoNome = ultimoNome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.publicacoes = Collections.emptyList();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank
    @Setter
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Setter
    @JsonIgnore
    @Size(max = 120)
    private String senha;

    @NotBlank
    @Setter
    @Size(max = 120)
    private String primeiroNome;

    @NotBlank
    @Setter
    @Size(max = 120)
    private String ultimoNome;

    @NotBlank
    @Setter
    @JsonIgnore
    @Size(max = 11)
    private String cpf;

    @ManyToMany(fetch = FetchType.LAZY)
    @Setter
    @Getter
    @JsonIgnore
    @JoinTable(name = "usuario_funcoes", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "funcao_id"))
    private Set<Funcao> funcoes = new HashSet<>();

    @Getter
    @Setter
    @OneToMany(mappedBy = "autor")
    @JsonIgnore
    private List<Publicacao> publicacoes;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    @Setter
    @JsonIgnore
    private List<Comentario> comentarios;


}
