package proyecto_legajo.legajo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import proyecto_legajo.legajo.Entity.usuarios;
import proyecto_legajo.legajo.Repository.usuarioRepository;
import proyecto_legajo.legajo.Security.UsuarioPrincipal;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private usuarioRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        usuarios u = repo.findByCorreo(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return new UsuarioPrincipal(u);
    }
}
