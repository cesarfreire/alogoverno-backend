package br.udesc.alogoverno.repositorio;

import br.udesc.alogoverno.modelo.Apoio;
import br.udesc.alogoverno.modelo.Publicacao;
import br.udesc.alogoverno.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApoioRepositorio extends JpaRepository<Apoio, Long> {
    Optional<Apoio> findByUsuarioAndPublicacao(Usuario usuario, Publicacao publicacao);
    long countByPublicacao(Publicacao publicacao);
    List<Apoio> findByPublicacao(Publicacao publicacao);
}
