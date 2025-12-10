package proyecto_legajo.legajo.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import proyecto_legajo.legajo.Entity.roles;
import proyecto_legajo.legajo.Entity.usuarios;
import proyecto_legajo.legajo.Repository.usuarioRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

/**
 * Inicializador que crea o actualiza un usuario admin al arrancar la aplicación.
 */
@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private usuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EntityManager entityManager;

    // Contraseña por defecto a aplicar. Puedes cambiarla aquí o dejarla.
    private final String ADMIN_EMAIL = "admin@legajo.com";
    private final String ADMIN_PASSWORD = "admin123";

    @Override
    public void run(String... args) throws Exception {
        // Disabled - causes session closure issues during startup
        // AdminInitializer logic moved to a separate service to be called on-demand
    }
}
