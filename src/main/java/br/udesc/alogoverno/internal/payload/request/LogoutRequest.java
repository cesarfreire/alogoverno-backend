package br.udesc.alogoverno.internal.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LogoutRequest {

    @NotBlank
    private String email;
}
