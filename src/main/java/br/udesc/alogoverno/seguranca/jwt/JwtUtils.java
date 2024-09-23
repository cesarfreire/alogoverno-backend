package br.udesc.alogoverno.seguranca.jwt;

import br.udesc.alogoverno.servicos.UsuarioDetalhesImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @Value("${alogoverno.app.jwtSecret}")
    private String jwtSecret;

    @Value("${alogoverno.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String gerarJwtToken(Authentication authentication) {
        UsuarioDetalhesImpl usuarioPri = (UsuarioDetalhesImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((usuarioPri.getEmail()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String gerarJwtTokenFromEmail(String email){
        return Jwts.builder().setSubject(email).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }


    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsuarioEmailFromTokenJwt(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();
    }

    public Boolean validaTokenJwt(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Token JWT inválido: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Token JWT expirado: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Token JWT nao eh suportado: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT eh vazio: {}", e.getMessage());
        }
        return false;
    }
}
