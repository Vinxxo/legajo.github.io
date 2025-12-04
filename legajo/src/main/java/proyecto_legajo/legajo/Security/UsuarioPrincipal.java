package proyecto_legajo.legajo.Security;

import proyecto_legajo.legajo.Entity.usuarios;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UsuarioPrincipal implements UserDetails {

    private final usuarios user;

    public UsuarioPrincipal(usuarios user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user.getRol() == null) return List.of();
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRol().getRol().toUpperCase()));
    }

    

    @Override
    public String getPassword() { return user.getClave(); }


    @Override
    public String getUsername() { return user.getCorreo(); }

    public usuarios getUsuario() { return user; }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return Boolean.TRUE.equals(user.getActivo()); }
}
