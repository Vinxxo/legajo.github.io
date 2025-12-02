package proyecto_legajo.legajo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import proyecto_legajo.legajo.Dto.UsuarioDTO;
import proyecto_legajo.legajo.Entity.usuarios;
import proyecto_legajo.legajo.Repository.usuarioRepository;
import proyecto_legajo.legajo.Security.JwtUtil;
import proyecto_legajo.legajo.Service.UsuarioService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private usuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        // Accept both {"username","password"} and {"correo","clave"} to be compatible with frontend
        String username = body.getOrDefault("username", body.get("correo"));
        String password = body.getOrDefault("password", body.get("clave"));

        if (username == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Faltan campos de autenticación (username/password o correo/clave)"));
        }

        try {
            logger.info("Intento de login para usuario: {}", username);
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(auth);
            String token = null;
            try {
                token = jwtUtil.generateToken(username);
                logger.info("Token generado (preview) para {}: {}", username, token == null ? "null" : (token.length() > 8 ? token.substring(0,8) + "..." : token));
            } catch (Exception e) {
                logger.error("Error generando token JWT para usuario {}", username, e);
                // Return 500 so frontend can see server error
                return ResponseEntity.status(500).body(Map.of("error", "Error interno al generar token"));
            }

            // Obtener rol del usuario para que el frontend pueda redirigir según rol
            usuarios u = usuarioRepository.findByCorreo(username).orElse(null);
            String role = null;
            if (u != null && u.getRol() != null) role = u.getRol().getRol();
            return ResponseEntity.ok(Map.of("token", token, "role", role));
        } catch (Exception ex) {
            logger.error("Error autenticando usuario {}", username, ex);
            return ResponseEntity.status(401).body(Map.of("error", "Credenciales inválidas"));
        }
    }

    

    @GetMapping("/me")
    public ResponseEntity<?> me() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return ResponseEntity.status(401).build();
        String correo = auth.getName();
        usuarios u = usuarioRepository.findByCorreo(correo).orElse(null);
        if (u == null) return ResponseEntity.status(404).build();
        UsuarioDTO dto = usuarioService.toDto(u);
        return ResponseEntity.ok(dto);
    }
}
