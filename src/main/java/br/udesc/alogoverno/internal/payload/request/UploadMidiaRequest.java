package br.udesc.alogoverno.internal.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadMidiaRequest {
    @NotBlank
    private String fileName;
}
