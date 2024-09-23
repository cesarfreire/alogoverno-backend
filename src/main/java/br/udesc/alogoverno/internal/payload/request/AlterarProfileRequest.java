package br.udesc.alogoverno.internal.payload.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AlterarProfileRequest {
    private String primeiroNome;
    private String ultimoNome;
    private String email;
}
