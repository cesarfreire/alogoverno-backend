package br.udesc.alogoverno.advice;

import br.udesc.alogoverno.internal.exceptions.InvalidUuidException;
import br.udesc.alogoverno.internal.exceptions.PublicacaoNotFoundException;
import br.udesc.alogoverno.seguranca.jwt.exception.TokenRefreshException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class PublicacaoControladorAdvice {
    @ExceptionHandler(value = PublicacaoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MensagemErro handlePublicacaoNotFoundException(PublicacaoNotFoundException ex, WebRequest request) {
        return new MensagemErro(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(value = InvalidUuidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MensagemErro handleInvalidUuidException(InvalidUuidException ex, WebRequest request) {
        return new MensagemErro(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
    }
}
