package br.udesc.alogoverno.servicos;

import br.udesc.alogoverno.modelo.Apoio;
import br.udesc.alogoverno.modelo.Publicacao;
import br.udesc.alogoverno.modelo.Usuario;
import br.udesc.alogoverno.repositorio.ApoioRepositorio;
import br.udesc.alogoverno.repositorio.PublicacaoRepositorio;
import br.udesc.alogoverno.repositorio.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ApoioServico {
    @Autowired
    private ApoioRepositorio apoioRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private PublicacaoRepositorio publicacaoRepositorio;



    @Transactional
    public boolean apoiarPostagem(Long usuarioId, UUID publicacaoUuid) {
        Optional<Usuario> usuario = usuarioRepositorio.findById(usuarioId);
        Optional<Publicacao> publicacao = publicacaoRepositorio.getPublicacaoByUuid(publicacaoUuid);

        if (usuario.isPresent() && publicacao.isPresent()) {
            Optional<Apoio> apoioExistente = apoioRepositorio.findByUsuarioAndPublicacao(usuario.get(), publicacao.get());
            if (apoioExistente.isEmpty()) {
                Apoio apoio = new Apoio(usuario.get(), publicacao.get());
                apoioRepositorio.save(apoio);
                return true;
            }
        }
        return false;
    }

    public long contarApoiosPorPublicacao(UUID publicacaoUuid) {
        Optional<Publicacao> postagem = publicacaoRepositorio.getPublicacaoByUuid(publicacaoUuid);
        return postagem.map(value -> apoioRepositorio.countByPublicacao(value)).orElse(0L);
    }


    public List<Long> listarUsuariosQueApoiaram(UUID publicacaoUuid) {
        Optional<Publicacao> publicacao = publicacaoRepositorio.getPublicacaoByUuid(publicacaoUuid);
        return publicacao.map(value -> apoioRepositorio.findByPublicacao(value)
                .stream()
                .map(apoio -> apoio.getUsuario().getId())
                .toList()).orElse(List.of());
    }

    @Transactional
    public boolean removerApoio(Long usuarioId, UUID publicacaoUuid) {
        Optional<Usuario> usuario = usuarioRepositorio.findById(usuarioId);
        Optional<Publicacao> publicacao = publicacaoRepositorio.getPublicacaoByUuid(publicacaoUuid);

        if (usuario.isPresent() && publicacao.isPresent()) {
            Optional<Apoio> apoioExistente = apoioRepositorio.findByUsuarioAndPublicacao(usuario.get(), publicacao.get());
            apoioExistente.ifPresent(apoioRepositorio::delete);
            return apoioExistente.isPresent();
        }
        return false;
    }
}
