package br.udesc.alogoverno.internal.payload.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CriarPublicacaoRequest {
    @NotBlank
    @Size(max = 150)
    private String titulo;
    @NotBlank
    private String descricao;
    @Min(1)
    private Long usuarioId;
    private List<String> midias;
}
