package br.udesc.alogoverno.internal.payload.response;

import br.udesc.alogoverno.modelo.Publicacao;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublicacaoCriadaResponse {
    @NotBlank
    private Publicacao publicacao;
    @NotBlank
    private String mensagem;

    public PublicacaoCriadaResponse(Publicacao publicacao, String mensagem) {
        this.publicacao = publicacao;
        this.mensagem = mensagem;
    }
}
