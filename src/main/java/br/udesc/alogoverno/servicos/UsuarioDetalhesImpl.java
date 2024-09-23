package br.udesc.alogoverno.servicos;

import br.udesc.alogoverno.modelo.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UsuarioDetalhesImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    @Getter
    private Long id;

    @Getter
    @Setter
    private String primeiroNome;
    @Getter
    @Setter
    private String ultimoNome;

    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String cpf;

    @Getter
    @Setter
    @JsonIgnore
    private String senha;

    @Getter
    @Setter
    private Collection<? extends GrantedAuthority> autoridades;

    public UsuarioDetalhesImpl(Long id, String primeiroNome, String ultimoNome,
                               String email, String senha, String cpf,
                           Collection<? extends GrantedAuthority> autoridades) {
        this.id = id;
        this.primeiroNome = primeiroNome;
        this.ultimoNome = ultimoNome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.autoridades = autoridades;
    }

    public static UsuarioDetalhesImpl build(Usuario usuario) {
        List<GrantedAuthority> autoridades = usuario.getFuncoes().stream()
                .map(funcao -> new SimpleGrantedAuthority(funcao.getNome().name()))
                .collect(Collectors.toList());

        return new UsuarioDetalhesImpl(usuario.getId(),
                usuario.getPrimeiroNome(),
                usuario.getUltimoNome(),
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getCpf(),
                autoridades);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return autoridades;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UsuarioDetalhesImpl user = (UsuarioDetalhesImpl) o;
        return Objects.equals(id, user.id);
    }
}
