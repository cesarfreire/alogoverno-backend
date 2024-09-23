package br.udesc.alogoverno.dto;

import br.udesc.alogoverno.modelo.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.memory.UserAttribute;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublicacaoDTO {
    private Long id;
    private UUID uuid;
    private Date dataCriacao;
    private String titulo;
    private String descricao;
    private Usuario autor;
    private Long quantidadeApoios;
    private List<Long> apoiadores;
    private List<String> midias;
}
