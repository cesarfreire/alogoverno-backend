package br.udesc.alogoverno.internal.payload.request;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RemoverApoioPublicacaoRequest {
    private UUID publicacaoUuid;
    @Min(1)
    private Long usuarioId;
}
