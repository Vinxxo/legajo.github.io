package proyecto_legajo.legajo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            usuarios u = repo.findByCorreo(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
            return new UsuarioPrincipal(u);
        } catch (IncorrectResultSizeDataAccessException ex) {
            logger.error("Se encontraron múltiples usuarios con el mismo correo: {}", username);
            throw new UsernameNotFoundException("Usuario ambiguo: existen múltiples cuentas con ese correo");
        }
    }
}
