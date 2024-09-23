package br.udesc.alogoverno.internal.payload.response;

import br.udesc.alogoverno.modelo.Publicacao;
import br.udesc.alogoverno.modelo.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetTodasPublicacoesResponse {

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
