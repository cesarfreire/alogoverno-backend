package br.udesc.alogoverno.servicos;

import br.udesc.alogoverno.modelo.RefreshToken;
import br.udesc.alogoverno.repositorio.RefreshTokenRepositorio;
import br.udesc.alogoverno.repositorio.UsuarioRepositorio;
import br.udesc.alogoverno.seguranca.jwt.exception.TokenRefreshException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServico {
    @Value("${alogoverno.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepositorio refreshTokenRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepositorio.findByToken(token);
    }

    public Optional<RefreshToken> findByUsuarioId(Long usuarioId) {
        return refreshTokenRepositorio.findByUsuarioId(usuarioId);
    }

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUsuario(usuarioRepositorio.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepositorio.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepositorio.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public boolean existsTokenByUsuarioId(Long userId) {
        return refreshTokenRepositorio.existsByUsuarioId(usuarioRepositorio.findById(userId).get().getId());
    }

    @Transactional
    public boolean isValidToken(RefreshToken token) {
        return token.getExpiryDate().compareTo(Instant.now()) > 0;
    }


    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepositorio.deleteByUsuario(usuarioRepositorio.findById(userId).get());
    }
}
