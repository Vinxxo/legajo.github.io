package proyecto_legajo.legajo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import proyecto_legajo.legajo.Entity.usuarios;
import proyecto_legajo.legajo.Service.UsuarioService;
import proyecto_legajo.legajo.Dto.UsuarioDTO;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin("*")  // Permite conexión desde tu frontend
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Crear usuario
    @PostMapping
    public UsuarioDTO crearUsuario(@RequestBody UsuarioDTO usuarioDto) {
        usuarios saved = usuarioService.crearUsuario(usuarioService.fromDto(usuarioDto));
        return usuarioService.toDto(saved);
    }

    // Listar todos los usuarios
    @GetMapping
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    // Buscar usuario por ID
    @GetMapping("/{id}")
    public UsuarioDTO obtenerUsuarioPorId(@PathVariable int id) {
        return usuarioService.toDto(usuarioService.obtenerUsuarioPorId(id));
    }

    // Actualizar usuario
    @PutMapping("/{id}")
    public UsuarioDTO actualizarUsuario(@PathVariable int id, @RequestBody UsuarioDTO usuarioActualizado) {
        usuarios entidad = usuarioService.fromDto(usuarioActualizado);
        usuarios updated = usuarioService.actualizarUsuario(id, entidad);
        return usuarioService.toDto(updated);
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public String eliminarUsuario(@PathVariable int id) {
        usuarioService.eliminarUsuario(id);
        return "Usuario eliminado correctamente";
    }
}
