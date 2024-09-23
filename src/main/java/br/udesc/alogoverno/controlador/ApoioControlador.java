package br.udesc.alogoverno.controlador;

import br.udesc.alogoverno.internal.exceptions.InvalidUuidException;
import br.udesc.alogoverno.internal.payload.request.ApoiarPublicacaoRequest;
import br.udesc.alogoverno.internal.payload.request.RemoverApoioPublicacaoRequest;
import br.udesc.alogoverno.internal.utils.UuidUtils;
import br.udesc.alogoverno.modelo.Usuario;
import br.udesc.alogoverno.servicos.ApoioServico;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/apoios")
public class ApoioControlador {
    @Autowired
    private ApoioServico apoioServico;

    @PostMapping("/apoiar")
    public ResponseEntity<?> apoiarPostagem(@Valid @RequestBody ApoiarPublicacaoRequest request) {
        boolean apoiado = apoioServico.apoiarPostagem(request.getUsuarioId(), request.getPublicacaoUuid());
        if (apoiado) {
            return ResponseEntity.ok("Postagem apoiada com sucesso!");
        } else {
            return ResponseEntity.badRequest().body("Erro: O usuário já apoiou esta postagem ou os dados são inválidos.");
        }
    }

    @DeleteMapping("/remover")
    public ResponseEntity<?> removerApoio(@Valid @RequestBody RemoverApoioPublicacaoRequest request) {
        boolean removido = apoioServico.removerApoio(request.getUsuarioId(), request.getPublicacaoUuid());
        if (removido) {
            return ResponseEntity.ok("Apoio removido com sucesso!");
        } else {
            return ResponseEntity.badRequest().body("Erro: Não foi possível remover o apoio.");
        }
    }

    @GetMapping("/publicacao/{publicacaoUuid}")
    public ResponseEntity<Long> contarApoios(@PathVariable String publicacaoUuid) {
        // Validação do formato do UUID
        if (!UuidUtils.isValidUuid(publicacaoUuid)){
            throw new InvalidUuidException(publicacaoUuid, "UUID inválido!");
        }
        long contagem = apoioServico.contarApoiosPorPublicacao(UUID.fromString(publicacaoUuid));
        return ResponseEntity.ok(contagem);
    }

    @GetMapping("/usuarios/{publicacaoUuid}")
    public ResponseEntity<List<Long>> listarUsuariosQueApoiaram(@PathVariable String publicacaoUuid) {
        // Validação do formato do UUID
        if (!UuidUtils.isValidUuid(publicacaoUuid)){
            throw new InvalidUuidException(publicacaoUuid, "UUID inválido!");
        }
        List<Long> usuarios = apoioServico.listarUsuariosQueApoiaram(UUID.fromString(publicacaoUuid));
        if (usuarios.isEmpty()) {
            return ResponseEntity.ok(List.of());
        } else {
            return ResponseEntity.ok(usuarios);
        }
    }
}
