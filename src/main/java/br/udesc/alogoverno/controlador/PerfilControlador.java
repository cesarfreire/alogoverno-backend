package br.udesc.alogoverno.controlador;

import br.udesc.alogoverno.internal.exceptions.ProfileNotFoundException;
import br.udesc.alogoverno.internal.payload.request.AlterarProfileRequest;
import br.udesc.alogoverno.internal.payload.request.ProfileRequest;
import br.udesc.alogoverno.internal.payload.request.TokenRefreshRequest;
import br.udesc.alogoverno.modelo.Usuario;
import br.udesc.alogoverno.repositorio.UsuarioRepositorio;
import br.udesc.alogoverno.servicos.UsuarioDetalhesImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
@RestController
@RequestMapping("/api/profile")
public class PerfilControlador {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @GetMapping
    public ResponseEntity<?> getProfile(@Valid Authentication authentication) {
        UsuarioDetalhesImpl userDetails = (UsuarioDetalhesImpl) authentication.getPrincipal();
        Usuario usuario = usuarioRepositorio.findById(userDetails.getId()).orElse(null);
        if (usuario == null) {
            throw new ProfileNotFoundException(userDetails.getId(), "Usuário não encontrado");
        }
        return ResponseEntity.ok(usuario);
    }

    @PatchMapping(consumes = "application/json")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody AlterarProfileRequest profileRequest, Authentication authentication) {
        UsuarioDetalhesImpl userDetails = (UsuarioDetalhesImpl) authentication.getPrincipal();
        Usuario usuario = usuarioRepositorio.findById(userDetails.getId()).orElse(null);
        if (usuario == null) {
            throw new ProfileNotFoundException(userDetails.getId(), "Usuário não encontrado");
        }
        if (profileRequest.getPrimeiroNome() != null) {
            usuario.setPrimeiroNome(profileRequest.getPrimeiroNome());
        }
        if (profileRequest.getUltimoNome() != null) {
            usuario.setUltimoNome(profileRequest.getUltimoNome());
        }
        if (profileRequest.getEmail() != null) {
            usuario.setEmail(profileRequest.getEmail());
        }
        usuarioRepositorio.save(usuario);
        return ResponseEntity.ok(usuario);
    }
}
