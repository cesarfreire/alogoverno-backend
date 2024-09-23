package br.udesc.alogoverno.controlador;

import br.udesc.alogoverno.internal.payload.request.LoginRequest;
import br.udesc.alogoverno.internal.payload.request.SignupRequest;
import br.udesc.alogoverno.internal.payload.request.TokenRefreshRequest;
import br.udesc.alogoverno.internal.payload.response.JwtResponse;
import br.udesc.alogoverno.internal.payload.response.MessageResponse;
import br.udesc.alogoverno.internal.payload.response.TokenRefreshResponse;
import br.udesc.alogoverno.modelo.EnumFuncoes;
import br.udesc.alogoverno.modelo.Funcao;
import br.udesc.alogoverno.modelo.RefreshToken;
import br.udesc.alogoverno.modelo.Usuario;
import br.udesc.alogoverno.repositorio.FuncaoRepositorio;
import br.udesc.alogoverno.repositorio.UsuarioRepositorio;
import br.udesc.alogoverno.seguranca.jwt.JwtUtils;
import br.udesc.alogoverno.seguranca.jwt.exception.TokenRefreshException;
import br.udesc.alogoverno.servicos.RefreshTokenServico;
import br.udesc.alogoverno.servicos.UsuarioDetalhesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthControlador {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Autowired
    FuncaoRepositorio funcaoRepositorio;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenServico refreshTokenServico;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.gerarJwtToken(authentication);

        UsuarioDetalhesImpl userDetails = (UsuarioDetalhesImpl) authentication.getPrincipal();
        List<String> funcoes = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .toList();

        RefreshToken refreshToken = refreshTokenServico.findByUsuarioId(userDetails.getId()).orElse(null);
        if (refreshToken == null) {
            refreshToken = refreshTokenServico.createRefreshToken(userDetails.getId());
        } else {
//            refreshToken = refreshTokenServico.isValidToken(refreshToken) ? refreshToken : refreshTokenServico.createRefreshToken(userDetails.getId());
            if (!refreshTokenServico.isValidToken(refreshToken)) {
                refreshTokenServico.deleteByUserId(userDetails.getId());
                refreshToken = refreshTokenServico.createRefreshToken(userDetails.getId());
            }
        }
//        RefreshToken refreshToken = refreshTokenServico.createRefreshToken(userDetails.getId());

        return ResponseEntity
                .ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), funcoes));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (usuarioRepositorio.existsByCpf(signUpRequest.getCpf())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: CPF is already taken!"));
        }

        if (usuarioRepositorio.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        Usuario usuario = new Usuario(
                signUpRequest.getPrimeiroNome(),
                signUpRequest.getUltimoNome(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getSenha()),
                signUpRequest.getCpf());

        Set<String> strFuncoes = signUpRequest.getFuncao();
        Set<Funcao> funcoes = new HashSet<>();

        if (strFuncoes == null) {
            Funcao userRole = funcaoRepositorio.findByNome(EnumFuncoes.FUNCAO_USUARIO)
                    .orElseThrow(() -> new RuntimeException("Erro: Funcao nao encontrada."));
            funcoes.add(userRole);
        } else {
            strFuncoes.forEach(role -> {
                switch (role) {
                    case "admin":
                        Funcao adminRole = funcaoRepositorio.findByNome(EnumFuncoes.FUNCAO_ADMINISTRADOR)
                                .orElseThrow(() -> new RuntimeException("Erro: Funcao nao encontrada."));
                        funcoes.add(adminRole);

                        break;
                    case "mod":
                        Funcao modRole = funcaoRepositorio.findByNome(EnumFuncoes.FUNCAO_MODERADOR)
                                .orElseThrow(() -> new RuntimeException("Erro: Funcao nao encontrada."));
                        funcoes.add(modRole);

                        break;
                    default:
                        Funcao userRole = funcaoRepositorio.findByNome(EnumFuncoes.FUNCAO_USUARIO)
                                .orElseThrow(() -> new RuntimeException("Erro: Funcao nao encontrada."));
                        funcoes.add(userRole);
                }
            });
        }

        usuario.setFuncoes(funcoes);
        usuarioRepositorio.save(usuario);

        return ResponseEntity.ok(new MessageResponse("Usuario cadastrado com sucesso!"));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenServico.findByToken(requestRefreshToken)
                .map(refreshTokenServico::verifyExpiration)
                .map(RefreshToken::getUsuario)
                .map(usuario -> {
                    String token = jwtUtils.gerarJwtTokenFromEmail(usuario.getEmail());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        UsuarioDetalhesImpl userDetails = (UsuarioDetalhesImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SecurityContextHolder.clearContext();
        Long userId = userDetails.getId();
        refreshTokenServico.deleteByUserId(userId);
        return ResponseEntity.ok(new MessageResponse("Log out successful!"));
    }

}
