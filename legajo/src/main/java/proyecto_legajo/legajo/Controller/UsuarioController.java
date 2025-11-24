package proyecto_legajo.legajo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import proyecto_legajo.legajo.Entity.usuarios;
import proyecto_legajo.legajo.Service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin("*")  // Permite conexión desde tu frontend
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Crear usuario
    @PostMapping
    public usuarios crearUsuario(@RequestBody usuarios usuario) {
        return usuarioService.crearUsuario(usuario);
    }

    // Listar todos los usuarios
    @GetMapping
    public List<usuarios> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    // Buscar usuario por ID
    @GetMapping("/{id}")
    public usuarios obtenerUsuarioPorId(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPorId(id);
    }

    // Actualizar usuario
    @PutMapping("/{id}")
    public usuarios actualizarUsuario(@PathVariable Long id, @RequestBody usuarios usuarioActualizado) {
        return usuarioService.actualizarUsuario(id, usuarioActualizado);
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return "Usuario eliminado correctamente";
    }
}
