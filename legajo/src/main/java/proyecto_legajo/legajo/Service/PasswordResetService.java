package proyecto_legajo.legajo.Service;

import java.util.Optional;
import java.util.Date;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

import proyecto_legajo.legajo.Entity.usuarios;
import proyecto_legajo.legajo.Repository.usuarioRepository;

@Service
public class PasswordResetService {

    private static final Logger logger = LoggerFactory.getLogger(PasswordResetService.class);

    @Autowired
    private usuarioRepository usuarioRepo;

    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.mail.enabled:false}")
    private boolean mailEnabled;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms}")
    private long jwtExpirationMs;

    // ------------------------------------------
    // 1. Crear token JWT para recuperar contraseña
    // ------------------------------------------
    public String createToken(String email) {
        Optional<usuarios> userOpt = usuarioRepo.findByCorreo(email);

        if (!userOpt.isPresent()) {
            logger.warn("Usuario con correo " + email + " no encontrado");
            return null;
        }

        usuarios user = userOpt.get();

        try {
            // Decodificar la clave secreta Base64
            SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret));

            // Crear token JWT con el ID del usuario como subject
            String token = Jwts.builder()
                    .setSubject(String.valueOf(user.getIdUsuario()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();

            logger.info("Token JWT creado para usuario: " + email);

            sendResetEmail(email, token);

            return token;
        } catch (Exception e) {
            logger.error("Error creando token JWT: " + e.getMessage(), e);
            return null;
        }
    }

    // ------------------------------------------
    // 2. Enviar email con el token
    // ------------------------------------------
    private void sendResetEmail(String email, String token) {
        String link = "http://localhost:8081/reset-password?token=" + token;

        if (!mailEnabled) {
            // Modo desarrollo: solo registrar en log
            logger.warn("=".repeat(80));
            logger.warn("MODO DESARROLLO: Email NO enviado a " + email);
            logger.warn("Link de recuperación: " + link);
            logger.warn("Token: " + token);
            logger.warn("=".repeat(80));
            return;
        }

        // Modo producción: enviar email real
        if (mailSender == null) {
            logger.error("JavaMailSender no está configurado");
            throw new RuntimeException("Servicio de email no disponible");
        }

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("Restablecer contraseña");
        msg.setText("Haz clic en el siguiente enlace para restablecer tu contraseña:\n\n" + link);
        msg.setFrom("0998dandan@gmail.com");

        try {
            logger.info("Intentando enviar email a: " + email);
            mailSender.send(msg);
            logger.info("Email enviado exitosamente a: " + email);
        } catch (Exception e) {
            logger.error("Error enviando email a " + email + ": " + e.getMessage(), e);
            throw new RuntimeException("Error al enviar email: " + e.getMessage(), e);
        }
    }

    // ------------------------------------------
    // 3. Cambiar contraseña si el token JWT es válido
    // ------------------------------------------
    public boolean resetPassword(String token, String newPassword) {
        try {
            // Decodificar la clave secreta Base64
            SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret));

            // Validar y extraer el subject (userId) del token JWT
            String userIdStr = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            Integer userId = Integer.parseInt(userIdStr);

            // Buscar el usuario por ID
            Optional<usuarios> userOpt = usuarioRepo.findById(userId);

            if (!userOpt.isPresent()) {
                logger.warn("Usuario con ID " + userId + " no encontrado");
                return false;
            }

            // Actualizar contraseña
            usuarios user = userOpt.get();
            user.setClave(passwordEncoder.encode(newPassword));
            usuarioRepo.save(user);

            logger.info("Contraseña actualizada exitosamente para usuario ID: " + userId);
            return true;

        } catch (Exception e) {
            logger.error("Error validando token JWT o reseteando contraseña: " + e.getMessage(), e);
            return false;
        }
    }

    // ------------------------------------------
    // Actualizar contraseña por correo (sin token)
    // ------------------------------------------
    public boolean updatePasswordByEmail(String email, String newPassword) {
        if (email == null || email.isBlank()) return false;
        Optional<usuarios> userOpt = usuarioRepo.findByCorreoIgnoreCase(email);
        if (!userOpt.isPresent()) {
            logger.warn("Usuario con correo " + email + " no encontrado");
            return false;
        }
        try {
            usuarios user = userOpt.get();
            user.setClave(passwordEncoder.encode(newPassword));
            usuarioRepo.save(user);
            logger.info("Contraseña actualizada por correo para: " + email);
            return true;
        } catch (Exception e) {
            logger.error("Error actualizando contraseña por correo: " + e.getMessage(), e);
            return false;
        }
    }
}
