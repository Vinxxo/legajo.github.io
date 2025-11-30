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
    @Transactional
    public void run(String... args) throws Exception {
        try {
            usuarios admin = usuarioRepository.findByCorreo(ADMIN_EMAIL).orElse(null);
            if (admin == null) {
                admin = new usuarios();
                admin.setPrimerNombre("Admin");
                admin.setPrimerApellido("Legajo");
                admin.setCorreo(ADMIN_EMAIL);
                admin.setTelefono(0L);
                admin.setDireccion("");
                admin.setCiudad("");
                admin.setActivo(true);
            }

            // Setear contraseña codificada
            admin.setClave(passwordEncoder.encode(ADMIN_PASSWORD));

            // Intentar asignar rol con id 1 si existe
            try {
                roles rol = entityManager.find(roles.class, 1L);
                if (rol != null) {
                    admin.setRol(rol);
                }
            } catch (NoResultException ignored) {
            }

            usuarioRepository.save(admin);
        } catch (Exception e) {
            // No detener el arranque si algo falla aquí; solo loguear en stderr
            System.err.println("AdminInitializer error: " + e.getMessage());
        }
    }
}
