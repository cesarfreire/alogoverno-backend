package br.udesc.alogoverno.internal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PublicacaoNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 2L;

    public PublicacaoNotFoundException(String uuid, String message) {
        super(String.format("Publicação não encontrada [%s]: %s", uuid, message));
    }

}
//@ResponseStatus(HttpStatus.FORBIDDEN)
//public class TokenRefreshException extends RuntimeException {
//
//    @Serial
//    private static final long serialVersionUID = 1L;
//
//    public TokenRefreshException(String token, String message) {
//        super(String.format("Failed for [%s]: %s", token, message));
//    }
//}