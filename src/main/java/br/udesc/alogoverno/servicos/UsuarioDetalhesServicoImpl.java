package br.udesc.alogoverno.servicos;

import br.udesc.alogoverno.modelo.Usuario;
import br.udesc.alogoverno.repositorio.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetalhesServicoImpl implements UserDetailsService {
    @Autowired
    UsuarioRepositorio usuarioRepositorio;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + email));
        return UsuarioDetalhesImpl.build(usuario);
    }
}
