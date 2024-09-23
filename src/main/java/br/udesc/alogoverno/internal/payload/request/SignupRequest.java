package br.udesc.alogoverno.internal.payload.request;

import java.util.Set;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequest {
    @NotBlank
    @Size(max = 60)
    private String primeiroNome;
    @NotBlank
    @Size(max = 60)
    private String ultimoNome;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 11)
    private String cpf;

    private Set<String> funcao;

    @NotBlank
    @Size(min = 10)
    private String senha;


}
