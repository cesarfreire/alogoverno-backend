package br.udesc.alogoverno.repositorio;

import br.udesc.alogoverno.modelo.EnumFuncoes;
import br.udesc.alogoverno.modelo.Funcao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuncaoRepositorio extends JpaRepository<Funcao, Long> {
    Optional<Funcao> findByNome(EnumFuncoes nome);
}
