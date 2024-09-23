package br.udesc.alogoverno.internal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProfileNotFoundException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 2L;

    public ProfileNotFoundException(Long userId, String message) {
        super(String.format("Usuário não encontrado [%s]: %s", userId, message));
    }
}
