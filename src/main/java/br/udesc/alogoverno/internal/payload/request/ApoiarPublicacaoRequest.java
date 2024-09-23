package br.udesc.alogoverno.internal.payload.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ApoiarPublicacaoRequest {
    private UUID publicacaoUuid;

    @Min(1)
    private Long usuarioId;


}
