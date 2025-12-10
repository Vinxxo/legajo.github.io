package proyecto_legajo.legajo.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import proyecto_legajo.legajo.Entity.PasswordResetToken;
import proyecto_legajo.legajo.Entity.usuarios;
import proyecto_legajo.legajo.Repository.PasswordResetTokenRepository;
import proyecto_legajo.legajo.Repository.usuarioRepository;

@Service
public class PasswordResetService {

    private static final Logger logger = LoggerFactory.getLogger(PasswordResetService.class);

    @Autowired
    private usuarioRepository usuarioRepo;

    @Autowired
    private PasswordResetTokenRepository tokenRepo;

    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.mail.enabled:false}")
    private boolean mailEnabled;

    // ------------------------------------------
    // 1. Crear token
    // ------------------------------------------
    public String createToken(String email) {
        Optional<usuarios> userOpt = usuarioRepo.findByCorreo(email);

        if (!userOpt.isPresent()) {
            return null;
        }

        usuarios user = userOpt.get();

        // Eliminar token anterior si existe para este usuario
        PasswordResetToken existingToken = tokenRepo.findByUsuario(user);
        if (existingToken != null) {
            tokenRepo.delete(existingToken);
            logger.info("Token anterior eliminado para usuario: " + email);
        }

        // Generar token único
        String token = UUID.randomUUID().toString();

        // Crear token con expiración
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 1); // expira en 1 hora

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUsuario(user);
        resetToken.setExpiracion(calendar.getTime());

        tokenRepo.save(resetToken);
        logger.info("Token creado para usuario: " + email);

        sendResetEmail(email, token);

        return token;
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
    // 3. Cambiar contraseña si el token es válido
    // ------------------------------------------
    public boolean resetPassword(String token, String newPassword) {
        PasswordResetToken prt = tokenRepo.findByToken(token);

        if (prt == null || prt.isExpired()) {
            return false;
        }

        usuarios user = prt.getUsuario();
        user.setClave(passwordEncoder.encode(newPassword));
        usuarioRepo.save(user);

        tokenRepo.delete(prt); // Token usado = token eliminado

        return true;
    }
}
