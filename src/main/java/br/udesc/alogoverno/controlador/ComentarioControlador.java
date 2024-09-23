package br.udesc.alogoverno.controlador;

import br.udesc.alogoverno.internal.exceptions.InvalidUuidException;
import br.udesc.alogoverno.internal.payload.request.CriarComentarioRequest;
import br.udesc.alogoverno.internal.utils.UuidUtils;
import br.udesc.alogoverno.modelo.Comentario;
import br.udesc.alogoverno.servicos.ComentarioServico;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comentarios")
public class ComentarioControlador {

    @Autowired
    private ComentarioServico comentarioServico;

    @GetMapping("/postagem/{postagemUuid}")
    public ResponseEntity<List<Comentario>> listarComentariosPorPostagem(@Valid @PathVariable String postagemUuid) {
        // Validação do formato do UUID
        if (!UuidUtils.isValidUuid(postagemUuid)){
            throw new InvalidUuidException(postagemUuid, "UUID inválido!");
        }
        return ResponseEntity.ok(comentarioServico.listarComentariosPorPostagem(UUID.fromString(postagemUuid)));
    }

    @PostMapping
    public ResponseEntity<Comentario> criarComentario(@Valid @RequestBody CriarComentarioRequest request) {

        Comentario novoComentario = comentarioServico.criarComentario(
                request.getDescricao(), request.getPublicacaoUuid(), request.getUsuarioId()
        );
        return ResponseEntity.ok(novoComentario);
    }

    @DeleteMapping("/{comentarioUuid}")
    public ResponseEntity<?> deletarComentario(@PathVariable UUID comentarioUuid) {
        boolean deletado = comentarioServico.deletarComentario(comentarioUuid);
        if (deletado) {
            return ResponseEntity.ok("Comentário deletado com sucesso!");
        } else {
            return ResponseEntity.badRequest().body("Erro ao deletar comentário!");
        }
    }
}
