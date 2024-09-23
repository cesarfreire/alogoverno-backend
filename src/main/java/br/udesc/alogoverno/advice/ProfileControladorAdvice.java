package br.udesc.alogoverno.advice;

import br.udesc.alogoverno.internal.exceptions.ProfileNotFoundException;
import br.udesc.alogoverno.internal.exceptions.PublicacaoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class ProfileControladorAdvice {
    @ExceptionHandler(value = ProfileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MensagemErro handleProfileNotFoundException(ProfileNotFoundException ex, WebRequest request) {
        return new MensagemErro(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
    }

}
