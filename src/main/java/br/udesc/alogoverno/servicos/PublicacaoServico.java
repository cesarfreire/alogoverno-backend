package br.udesc.alogoverno.servicos;

import br.udesc.alogoverno.dto.PublicacaoDTO;
import br.udesc.alogoverno.internal.exceptions.PublicacaoNotFoundException;
import br.udesc.alogoverno.internal.payload.response.GetTodasPublicacoesResponse;
import br.udesc.alogoverno.modelo.Publicacao;
import br.udesc.alogoverno.modelo.RespostaOficial;
import br.udesc.alogoverno.modelo.Usuario;
import br.udesc.alogoverno.repositorio.ApoioRepositorio;
import br.udesc.alogoverno.repositorio.PublicacaoRepositorio;
import br.udesc.alogoverno.repositorio.RespostaOficialRepositorio;
import br.udesc.alogoverno.repositorio.UsuarioRepositorio;
import br.udesc.alogoverno.internal.payload.response.GetPublicacaoResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PublicacaoServico {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private PublicacaoRepositorio publicacaoRepositorio;

    @Autowired
    private ApoioRepositorio apoioRepositorio;

    @Autowired
    private ApoioServico apoioServico;

    @Autowired
    private RespostaOficialRepositorio respostaOficialRepositorio;
    @Transactional
    public Publicacao criarPublicacao(Publicacao novaPublicacao, Usuario autor){

        Publicacao publicacaoSalva = publicacaoRepositorio.save(novaPublicacao);

        autor.getPublicacoes().add(publicacaoSalva);
        usuarioRepositorio.save(autor);
        return publicacaoSalva;
    }

    @Transactional
    public List<Publicacao> listarPublicacoes(){
        return publicacaoRepositorio.findAll();
    }

    @Transactional
    public List<GetTodasPublicacoesResponse> listarPublicacoesComApoios(){
        List<Publicacao> publicacoes = publicacaoRepositorio.findAll();
        return publicacoes.stream().map(publicacao -> {
            Long quantidadeApoios = apoioRepositorio.countByPublicacao(publicacao);
            List<Long> apoiadores = apoioServico.listarUsuariosQueApoiaram(publicacao.getUuid());
            return new GetTodasPublicacoesResponse(
                    publicacao.getId(),
                    publicacao.getUuid(),
                    publicacao.getDataCriacao(),
                    publicacao.getTitulo(),
                    publicacao.getDescricao(),
                    publicacao.getAutor(),
                    quantidadeApoios,
                    apoiadores,
                    publicacao.getMidias()
            );
        }).collect(Collectors.toList());
    }

    @Transactional
    public boolean deletarPublicacao(UUID uuid){
        Publicacao publicacao = publicacaoRepositorio.getPublicacaoByUuid(uuid).orElse(null);
        if (publicacao == null){
            throw new PublicacaoNotFoundException(String.valueOf(uuid), "Publicação não encontrada");
        }
        publicacaoRepositorio.delete(publicacao);
        return true;
    }

    @Transactional
    public GetPublicacaoResponse getPublicacao(UUID uuid){
        Publicacao publicacao = publicacaoRepositorio.getPublicacaoByUuid(uuid).orElse(null);
        List<Long> apoiadores = apoioServico.listarUsuariosQueApoiaram(uuid);
        List<RespostaOficial> respostasOficiais = respostaOficialRepositorio.findByPublicacaoUuid(uuid)
                .orElse(null);
        if (publicacao == null){
            return null;
        }
        Long quantidadeApoios = apoioRepositorio.countByPublicacao(publicacao);
        return new GetPublicacaoResponse(
                publicacao.getId(),
                publicacao.getUuid(),
                publicacao.getDataCriacao(),
                publicacao.getTitulo(),
                publicacao.getDescricao(),
                publicacao.getAutor(),
                quantidadeApoios,
                apoiadores,
                publicacao.getMidias(),
                respostasOficiais
        );
    }

}
