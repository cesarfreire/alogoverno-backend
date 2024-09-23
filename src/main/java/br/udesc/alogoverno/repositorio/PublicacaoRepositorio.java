package br.udesc.alogoverno.repositorio;

import br.udesc.alogoverno.modelo.Publicacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PublicacaoRepositorio extends JpaRepository<Publicacao, Long> {
    Optional<Publicacao> getPublicacaoByUuid (UUID uuid);
}
