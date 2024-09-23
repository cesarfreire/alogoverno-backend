package br.udesc.alogoverno.internal.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String senha;

}