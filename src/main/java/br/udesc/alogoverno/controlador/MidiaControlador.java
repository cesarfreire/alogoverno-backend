package br.udesc.alogoverno.controlador;

import br.udesc.alogoverno.internal.payload.request.UploadMidiaRequest;
import br.udesc.alogoverno.servicos.MidiaServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
@RequestMapping("/api/storage/")
public class MidiaControlador {

    @Autowired
    private MidiaServico midiaServico;

    @PostMapping(value = "/url-assinada", consumes = "application/json")
    public ResponseEntity<?> getUrlAssinada(@RequestBody UploadMidiaRequest request){
        URL urlPreAssinada = midiaServico.gerarUrlAssinada(request.getFileName());

        return ResponseEntity.ok(urlPreAssinada.toString());
    }
}
