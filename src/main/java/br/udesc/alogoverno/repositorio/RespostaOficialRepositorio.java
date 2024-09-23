package br.udesc.alogoverno.repositorio;

import br.udesc.alogoverno.modelo.RespostaOficial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RespostaOficialRepositorio extends JpaRepository<RespostaOficial, Long> {
    Optional<List<RespostaOficial>> findByPublicacaoUuid(UUID uuid);
}
