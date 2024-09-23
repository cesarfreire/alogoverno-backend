package br.udesc.alogoverno.internal.payload.request;

import br.udesc.alogoverno.modelo.Comentario;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CriarComentarioRequest {
    @NotBlank
    @Size(max = 350)
    private String descricao;

    private UUID publicacaoUuid;
    @Min(1)
    private Long usuarioId;
//    @NotBlank
//    private Comentario comentario;
}
