package proyecto_legajo.legajo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import proyecto_legajo.legajo.Dto.LibroDTO;

import proyecto_legajo.legajo.Service.LibrosService;
import proyecto_legajo.legajo.Dto.LibroResponseDTO;
import proyecto_legajo.legajo.Entity.usuarios;
import proyecto_legajo.legajo.Repository.usuarioRepository;

@RestController
@RequestMapping("/api/libros")
@CrossOrigin("*")

public class LibrosRestController {

    private final LibrosService service;
    
    @Autowired
    private usuarioRepository usuarioRepository;

    public LibrosRestController(LibrosService service) {
        this.service = service;
    }

    @GetMapping
    public List<LibroResponseDTO> listar(
        @RequestParam(required = false) String usuario,
        @RequestParam(required = false) String titulo,
        @RequestParam(required = false) String autor,
        @RequestParam(required = false) String genero,
        @RequestParam(required = false) String estado
    ) {
        return service.buscarPorFiltros(usuario, titulo, autor, genero, estado);
    }

    // Obtener libro por ID
    @GetMapping("/{id}")
    public ResponseEntity<LibroDTO> getLibro(@PathVariable int id) {
        LibroDTO libro = service.obtenerPorId(id);
        if (libro == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(libro);
    }

    // Crear libro
    @PostMapping
    public ResponseEntity<?> crearLibro(@RequestBody LibroDTO libroDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        // Si no hay autenticación o es anónimo, rechazar
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Se requiere autenticación para registrar un libro"));
        }
        
        String correo = auth.getName();
        usuarios usuario = usuarioRepository.findByCorreo(correo).orElse(null);
        
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Usuario no encontrado"));
        }
        
        LibroDTO creado = service.crearLibro(libroDTO, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // Actualizar libro
    @PutMapping("/{id}")
    public ResponseEntity<LibroDTO> actualizarLibro(@PathVariable int id, @RequestBody LibroDTO libroDTO) {
        LibroDTO actualizado = service.actualizarLibro(id, libroDTO);
        if (actualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar libro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLibro(@PathVariable int id) {
        boolean eliminado = service.eliminarLibro(id);
        if (!eliminado) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}
