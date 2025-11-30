package proyecto_legajo.legajo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private usuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        // Accept both {"username","password"} and {"correo","clave"} to be compatible with frontend
        String username = body.getOrDefault("username", body.get("correo"));
        String password = body.getOrDefault("password", body.get("clave"));

        if (username == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Faltan campos de autenticación (username/password o correo/clave)"));
        }

        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(auth);
            String token = jwtUtil.generateToken(username);
            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception ex) {
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
