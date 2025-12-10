package proyecto_legajo.legajo.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import proyecto_legajo.legajo.Dto.IntercambioDTO;
import proyecto_legajo.legajo.Entity.intercambios;
import proyecto_legajo.legajo.Entity.libros;
import proyecto_legajo.legajo.Entity.usuarios;
import proyecto_legajo.legajo.Entity.EstadoIntercambio;
import proyecto_legajo.legajo.Repository.IntercambiosRepository;
import proyecto_legajo.legajo.Repository.LibrosRepository;
import proyecto_legajo.legajo.Repository.usuarioRepository;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/intercambios")
@CrossOrigin("*")
public class IntercambiosController {

    private static final Logger logger = LoggerFactory.getLogger(IntercambiosController.class);

    @Autowired
    private IntercambiosRepository intercambiosRepo;

    @Autowired
    private usuarioRepository usuarioRepo;

    @Autowired
    private LibrosRepository librosRepo;

    // Solicitar intercambio: body { libroId }
    @PostMapping("/request")
    public ResponseEntity<?> requestExchange(@RequestBody Map<String, Integer> body) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(401).body(Map.of("error", "Autenticación requerida"));
        }
        String correo = auth.getName();
        usuarios solicitante = usuarioRepo.findByCorreo(correo).orElse(null);
        if (solicitante == null) return ResponseEntity.status(401).body(Map.of("error","Usuario no encontrado"));

        Integer libroId = body.get("libroId");
        if (libroId == null) return ResponseEntity.badRequest().body(Map.of("error","libroId es requerido"));

        libros libro = librosRepo.findById(libroId).orElse(null);
        if (libro == null) return ResponseEntity.notFound().build();

        // no solicitar tu propio libro
        if (libro.getUsuarioPropietario().getIdUsuario() == solicitante.getIdUsuario()) {
            return ResponseEntity.badRequest().body(Map.of("error","No puedes solicitar tu propio libro"));
        }

        intercambios i = new intercambios();
        i.setUsuarioSolicitante(solicitante);
        i.setUsuarioReceptor(libro.getUsuarioPropietario());
        i.setLibroSolicitado(libro);
        i.setEstadoInter(EstadoIntercambio.pendiente);
        intercambiosRepo.save(i);
        logger.info("✓ Intercambio creado: id={}, solicitante={}, receptor={}, libro={}", 
            i.getIdIntercambio(), solicitante.getCorreo(), libro.getUsuarioPropietario().getCorreo(), libro.getTituloLib());

        // Return created exchange id
        return ResponseEntity.status(201).body(Map.of("id", i.getIdIntercambio()));
    }

    // Listar intercambios recibidos (pendientes) para usuario autenticado
    @GetMapping("/received")
    public ResponseEntity<?> received() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(401).body(Map.of("error", "Autenticación requerida"));
        }
        String correo = auth.getName();
        usuarios receptor = usuarioRepo.findByCorreo(correo).orElse(null);
        if (receptor == null) return ResponseEntity.status(401).body(Map.of("error","Usuario no encontrado"));

        List<intercambios> lista = intercambiosRepo.findByUsuarioReceptorIdUsuarioAndEstadoInter(receptor.getIdUsuario(), EstadoIntercambio.pendiente);
        List<IntercambioDTO> dtoList = lista.stream().map(this::toDto).collect(Collectors.toList());
        logger.info("✓ Intercambios pendientes listados para receptor={}, cantidad={}", receptor.getCorreo(), dtoList.size());
        return ResponseEntity.ok(dtoList);
    }

    // Obtener inventario del solicitante de un intercambio (para que receptor escoja libroCambio)
    @GetMapping("/{id}/requester-inventory")
    public ResponseEntity<?> requesterInventory(@PathVariable int id) {
        intercambios i = intercambiosRepo.findById(id).orElse(null);
        if (i == null) return ResponseEntity.notFound().build();
        usuarios solicitante = i.getUsuarioSolicitante();
        if (solicitante == null) return ResponseEntity.notFound().build();

        // Map to minimal book representation
        var books = solicitante.getLibros().stream().map(l -> Map.of(
            "id", l.getIdLibro(),
            "titulo", l.getTituloLib(),
            "urlImagen", l.getUrlImagen()
        )).collect(Collectors.toList());
        return ResponseEntity.ok(books);
    }

    // Responder intercambio: body { action: "accept"|"reject", libroCambioId (required if accept) }
    @PostMapping("/{id}/respond")
    public ResponseEntity<?> respond(@PathVariable int id, @RequestBody Map<String, String> body) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(401).body(Map.of("error", "Autenticación requerida"));
        }
        String correo = auth.getName();
        usuarios actor = usuarioRepo.findByCorreo(correo).orElse(null);
        if (actor == null) return ResponseEntity.status(401).body(Map.of("error","Usuario no encontrado"));

        intercambios i = intercambiosRepo.findById(id).orElse(null);
        if (i == null) return ResponseEntity.notFound().build();

        // Only receptor can accept/reject
        if (i.getUsuarioReceptor() == null || i.getUsuarioReceptor().getIdUsuario() != actor.getIdUsuario()) {
            return ResponseEntity.status(403).body(Map.of("error","No autorizado"));
        }

        String action = body.get("action");
        if (action == null) return ResponseEntity.badRequest().body(Map.of("error","action requerido"));

        if (action.equalsIgnoreCase("reject")) {
            i.setEstadoInter(EstadoIntercambio.rechazado);
            intercambiosRepo.save(i);
            logger.info("✓ Intercambio rechazado: id={}", i.getIdIntercambio());
            return ResponseEntity.ok(Map.of("status","rejected"));
        } else if (action.equalsIgnoreCase("accept")) {
            String libroCambioIdStr = body.get("libroCambioId");
            if (libroCambioIdStr == null) return ResponseEntity.badRequest().body(Map.of("error","libroCambioId requerido"));
            int libroCambioId = Integer.parseInt(libroCambioIdStr);
            libros libroCambio = librosRepo.findById(libroCambioId).orElse(null);
            if (libroCambio == null) return ResponseEntity.notFound().build();

            // Set exchange chosen book
            i.setLibroCambio(libroCambio);
            i.setEstadoInter(EstadoIntercambio.aceptado);
            i.setFechaConfirmacion(LocalDateTime.now());
            intercambiosRepo.save(i);
            logger.info("✓ Intercambio aceptado: id={}, libroCambio={}", i.getIdIntercambio(), libroCambio.getTituloLib());

            // Prepare WhatsApp link to open conversation between receptor (actor) and solicitante
            String phoneSolicitante = i.getUsuarioSolicitante().getTelefono() == null ? "" : i.getUsuarioSolicitante().getTelefono().toString();
            String phoneReceptor = i.getUsuarioReceptor().getTelefono() == null ? "" : i.getUsuarioReceptor().getTelefono().toString();

            // Create message
            String msg = String.format("Hola %s, acepté el intercambio del libro '%s' y ofrezco '%s'. Mi teléfono: %s. Coordinemos.",
                i.getUsuarioReceptor().getPrimerNombre(),
                i.getLibroSolicitado().getTituloLib(),
                i.getLibroCambio().getTituloLib(),
                i.getUsuarioReceptor().getTelefono() == null ? "" : i.getUsuarioReceptor().getTelefono().toString()
            );
            String encoded = URLEncoder.encode(msg, StandardCharsets.UTF_8);

            // Build wa.me URL to open chat with solicitante prefilled message
            String waUrl = "https://wa.me/" + phoneSolicitante + "?text=" + encoded;

            return ResponseEntity.ok(Map.of("status","accepted","whatsapp", waUrl));
        }

        return ResponseEntity.badRequest().body(Map.of("error","action desconocida"));
    }

    private IntercambioDTO toDto(intercambios i) {
        IntercambioDTO dto = new IntercambioDTO();
        dto.setIdIntercambio(i.getIdIntercambio());
        if (i.getUsuarioSolicitante() != null) {
            dto.setIdSolicitante(i.getUsuarioSolicitante().getIdUsuario());
            dto.setNombreSolicitante(i.getUsuarioSolicitante().getPrimerNombre() + " " + i.getUsuarioSolicitante().getPrimerApellido());
        }
        if (i.getUsuarioReceptor() != null) {
            dto.setIdReceptor(i.getUsuarioReceptor().getIdUsuario());
            dto.setNombreReceptor(i.getUsuarioReceptor().getPrimerNombre() + " " + i.getUsuarioReceptor().getPrimerApellido());
        }
        if (i.getLibroSolicitado() != null) {
            dto.setIdLibroSolicitado(i.getLibroSolicitado().getIdLibro());
            dto.setTituloSolicitado(i.getLibroSolicitado().getTituloLib());
        }
        if (i.getLibroCambio() != null) {
            dto.setIdLibroCambio(i.getLibroCambio().getIdLibro());
            dto.setTituloLibroCambio(i.getLibroCambio().getTituloLib());
        }
        dto.setEstadoInter(i.getEstadoInter() == null ? "" : i.getEstadoInter().name());
        dto.setFechaSolicitud(i.getFechaSolicitud());
        dto.setFechaConfirmacion(i.getFechaConfirmacion());
        return dto;
    }
}
