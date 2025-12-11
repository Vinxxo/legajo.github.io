package proyecto_legajo.legajo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;

import proyecto_legajo.legajo.Service.PasswordResetService;

@Controller
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @Value("${app.mail.enabled:false}")
    private boolean mailEnabled;

    // Vista donde el usuario pone su correo
    @GetMapping("/forgot-password")
    public String forgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgot(@RequestParam String email, Model model) {
        try {
            String token = passwordResetService.createToken(email);
            if (token == null) {
                model.addAttribute("error", "Correo no encontrado");
                return "forgot-password";
            }
            
            if (!mailEnabled) {
                // En modo desarrollo, mostrar el enlace directamente
                String resetLink = "http://localhost:8081/reset-password?token=" + token;
                model.addAttribute("message", "⚠️ MODO DESARROLLO: El enlace es:\n\n" + resetLink);
                model.addAttribute("devLink", resetLink);
            } else {
                model.addAttribute("message", "Se envió un enlace a tu correo. Revisa tu bandeja de entrada.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error al procesar la solicitud. Intenta más tarde.");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return "forgot-password";
    }

    // Endpoint para actualizar contraseña directamente por correo (sin token)
    @PostMapping("/forgot-password/update")
    public String processForgotUpdate(@RequestParam String email, @RequestParam String password, Model model) {
        try {
            boolean ok = passwordResetService.updatePasswordByEmail(email, password);
            if (!ok) {
                model.addAttribute("error", "Correo no encontrado o error al actualizar");
            } else {
                model.addAttribute("message", "Contraseña actualizada correctamente.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error al procesar la solicitud. Intenta más tarde.");
            e.printStackTrace();
        }
        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetForm(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String processReset(
            @RequestParam String token,
            @RequestParam String password,
            Model model) {

        boolean ok = passwordResetService.resetPassword(token, password);
        model.addAttribute("status", ok);
        return "reset-password";
    }
}
