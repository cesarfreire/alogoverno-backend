package br.udesc.alogoverno.repositorio;

import br.udesc.alogoverno.modelo.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ComentarioRepositorio extends JpaRepository<Comentario, Long> {
    List<Comentario> findByPublicacaoId(Long publicacaoId);
    List<Comentario> findByPublicacaoUuid(UUID publicacaoUuid);
    List<Comentario> findByAutorId(Long usuarioId);
    Optional<Comentario> findByUuid(UUID uuid);

}
