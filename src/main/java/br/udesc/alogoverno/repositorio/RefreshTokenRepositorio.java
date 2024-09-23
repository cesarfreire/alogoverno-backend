package br.udesc.alogoverno.repositorio;

import br.udesc.alogoverno.modelo.RefreshToken;
import br.udesc.alogoverno.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepositorio extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUsuarioId(Long usuarioId);
    void deleteByToken(String token);
    Boolean existsByUsuarioId(Long usuarioId);
    @Modifying
    int deleteByUsuario(Usuario usuario);
}
