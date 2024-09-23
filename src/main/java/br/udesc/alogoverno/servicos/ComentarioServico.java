package br.udesc.alogoverno.servicos;

import br.udesc.alogoverno.modelo.Comentario;
import br.udesc.alogoverno.modelo.Publicacao;
import br.udesc.alogoverno.modelo.Usuario;
import br.udesc.alogoverno.repositorio.ComentarioRepositorio;
import br.udesc.alogoverno.repositorio.PublicacaoRepositorio;
import br.udesc.alogoverno.repositorio.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ComentarioServico {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private PublicacaoRepositorio publicacaoRepositorio;

    @Autowired
    private ComentarioRepositorio comentarioRepositorio;

    public List<Comentario> listarComentariosPorPostagem(UUID postagemUuid) {
        return comentarioRepositorio.findByPublicacaoUuid(postagemUuid);
    }

    @Transactional
    public Comentario criarComentario(String comentario, UUID postagemUuid, Long autorId){
        Optional<Publicacao> publicacao = publicacaoRepositorio.getPublicacaoByUuid(postagemUuid);
        Optional<Usuario> autor = usuarioRepositorio.findById(autorId);
        if (publicacao.isEmpty()) {
            throw new RuntimeException("Postagem ou Usuário não encontrado");
        }
        if (autor.isEmpty()) {
            throw new RuntimeException("Autor não encontrado");
        }
        Comentario novoComentario = new Comentario();
        novoComentario.setDescricao(comentario);
        novoComentario.setPublicacao(publicacao.get());
        novoComentario.setAutor(autor.get());
        return comentarioRepositorio.save(novoComentario);
    }

    @Transactional
    public boolean deletarComentario(UUID comentarioUuid){
        Comentario comentario = comentarioRepositorio.findByUuid(comentarioUuid).orElse(null);
        if (comentario == null){
            throw new RuntimeException("Comentário não encontrado");
        }
        comentarioRepositorio.delete(comentario);
        return true;
    }
}
