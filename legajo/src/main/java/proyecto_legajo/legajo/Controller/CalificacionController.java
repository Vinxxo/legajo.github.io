package proyecto_legajo.legajo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import proyecto_legajo.legajo.Dto.CalificacionDTO;
import proyecto_legajo.legajo.Entity.usuarios;
import proyecto_legajo.legajo.Repository.usuarioRepository;
import proyecto_legajo.legajo.Service.CalificacionLibroService;
import proyecto_legajo.legajo.Service.CalificacionLibroService.PromedioCalificacionDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/calificaciones")
@CrossOrigin("*")
public class CalificacionController {

    private static final Logger logger = LoggerFactory.getLogger(CalificacionController.class);

    @Autowired
    private CalificacionLibroService calificacionService;

    @Autowired
    private usuarioRepository usuarioRepo;

    /**
     * POST /api/calificaciones
     * Crear o actualizar calificación
     * Body: { idLibro, calificacion }
     */
    @PostMapping
    public ResponseEntity<?> crearOActualizarCalificacion(@RequestBody Map<String, Object> body) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(401).body(Map.of("error", "Autenticación requerida"));
        }

        String correo = auth.getName();
        usuarios usuario = usuarioRepo.findByCorreo(correo).orElse(null);
        if (usuario == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Usuario no encontrado"));
        }

        try {
            Integer idLibro = null;
            if (body.get("idLibro") instanceof Integer) {
                idLibro = (Integer) body.get("idLibro");
            } else if (body.get("idLibro") instanceof Double) {
                idLibro = ((Double) body.get("idLibro")).intValue();
            }

            Integer calificacionInt = null;
            if (body.get("calificacion") instanceof Integer) {
                calificacionInt = (Integer) body.get("calificacion");
            } else if (body.get("calificacion") instanceof Double) {
                calificacionInt = ((Double) body.get("calificacion")).intValue();
            }

            if (idLibro == null || calificacionInt == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "idLibro y calificacion son requeridos"));
            }

            byte calificacion = calificacionInt.byteValue();

            CalificacionDTO resultado = calificacionService.guardarCalificacion(idLibro, calificacion, usuario);
            logger.info("✓ Calificación guardada: usuario={}, libro={}, calificacion={}", 
                usuario.getCorreo(), idLibro, calificacion);
            
            return ResponseEntity.status(201).body(resultado);

        } catch (RuntimeException e) {
            logger.error("Error al guardar calificación: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * GET /api/calificaciones/libros/{idLibro}/historial
     * Obtener todas las calificaciones de un libro (historial)
     */
    @GetMapping("/libros/{idLibro}/historial")
    public ResponseEntity<?> obtenerHistorialCalificaciones(@PathVariable int idLibro) {
        try {
            List<CalificacionDTO> calificaciones = calificacionService.obtenerCalificacionesDelLibro(idLibro);
            return ResponseEntity.ok(calificaciones);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET /api/calificaciones/libros/{idLibro}/promedio
     * Obtener promedio y cantidad de calificaciones
     */
    @GetMapping("/libros/{idLibro}/promedio")
    public ResponseEntity<?> obtenerPromedioLibro(@PathVariable int idLibro) {
        try {
            PromedioCalificacionDTO promedio = calificacionService.obtenerPromedioLibro(idLibro);
            return ResponseEntity.ok(promedio);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
