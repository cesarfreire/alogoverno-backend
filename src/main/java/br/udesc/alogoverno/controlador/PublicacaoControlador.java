package br.udesc.alogoverno.controlador;

import br.udesc.alogoverno.dto.PublicacaoDTO;
import br.udesc.alogoverno.internal.exceptions.InvalidUuidException;
import br.udesc.alogoverno.internal.exceptions.PublicacaoNotFoundException;
import br.udesc.alogoverno.internal.payload.request.CriarPublicacaoRequest;
import br.udesc.alogoverno.internal.payload.response.GetPublicacaoResponse;
import br.udesc.alogoverno.internal.payload.response.GetTodasPublicacoesResponse;
import br.udesc.alogoverno.internal.payload.response.MessageResponse;
import br.udesc.alogoverno.internal.payload.response.PublicacaoCriadaResponse;
import br.udesc.alogoverno.internal.utils.UuidUtils;
import br.udesc.alogoverno.modelo.Publicacao;
import br.udesc.alogoverno.modelo.Usuario;
import br.udesc.alogoverno.repositorio.UsuarioRepositorio;
import br.udesc.alogoverno.seguranca.jwt.AuthTokenFiltro;
import br.udesc.alogoverno.servicos.PublicacaoServico;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/publicacoes")
public class PublicacaoControlador {
    @Autowired
    private PublicacaoServico publicacaoServico;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    private static final Logger logger = LoggerFactory.getLogger(PublicacaoControlador.class);

    @PostMapping
    public ResponseEntity<?> criarPublicacao(
            @Valid @RequestBody CriarPublicacaoRequest criarPublicacaoRequest
    ){
        Usuario usuario = usuarioRepositorio.findById(criarPublicacaoRequest.getUsuarioId())
                .orElse(null);

        if (usuario == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Erro: Autor não encontrado!"));
        }

        Publicacao novaPublicacao = new Publicacao(
            criarPublicacaoRequest.getTitulo(),
            criarPublicacaoRequest.getDescricao()
        );

        novaPublicacao.setAutor(usuario);
        novaPublicacao.setMidias(criarPublicacaoRequest.getMidias());

        publicacaoServico.criarPublicacao(
            novaPublicacao,
                usuario

        );
        return ResponseEntity.ok(new PublicacaoCriadaResponse(novaPublicacao, "Publicação criada com sucesso!"));
    }

//    @GetMapping
//    public ResponseEntity<?> listarPublicacoes(){
//        List<Publicacao> publicacoes = publicacaoServico.listarPublicacoes();
//        return ResponseEntity.ok(new GetTodasPublicacoesResponse(publicacoes, "Publicações listadas com sucesso!"));
//    }
    @GetMapping
    public ResponseEntity<List<GetTodasPublicacoesResponse>> listarPublicacoes(){
        List<GetTodasPublicacoesResponse> publicacoes = publicacaoServico.listarPublicacoesComApoios();
        return ResponseEntity.ok(publicacoes);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getPublicacao(@PathVariable String uuid){

        // Validação do formato do UUID
        if (!UuidUtils.isValidUuid(uuid)){
            throw new InvalidUuidException(uuid, "UUID inválido!");
        }
        GetPublicacaoResponse publicacao = publicacaoServico.getPublicacao(UUID.fromString(uuid));
        if (publicacao == null){
            throw new PublicacaoNotFoundException(uuid, "Publicação não encontrada!");
        }
        return ResponseEntity.ok(publicacao);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deletarPublicacao(@PathVariable String uuid){
        // Validação do formato do UUID
        if (!UuidUtils.isValidUuid(uuid)){
            throw new InvalidUuidException(uuid, "UUID inválido!");
        }
        boolean deletado = publicacaoServico.deletarPublicacao(UUID.fromString(uuid));
        if (deletado){
            return ResponseEntity.ok(new MessageResponse("Publicação deletada com sucesso!"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Erro ao deletar publicação!"));
    }
}
