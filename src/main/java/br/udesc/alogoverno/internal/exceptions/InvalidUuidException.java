package br.udesc.alogoverno.internal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidUuidException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 2L;

    public InvalidUuidException(String uuid, String message) {
        super(String.format("UUID da publicação inválido [%s]: %s", uuid, message));
    }
}
