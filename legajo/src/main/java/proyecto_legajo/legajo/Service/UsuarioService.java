package proyecto_legajo.legajo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto_legajo.legajo.Entity.usuarios;
import proyecto_legajo.legajo.Repository.usuarioRepository;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private usuarioRepository usuarioRepository;

    // Crear usuario
    public usuarios crearUsuario(usuarios usuario) {
        return usuarioRepository.save(usuario);
    }

    // Listar todos los usuarios
    public List<usuarios> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Buscar usuario por ID
    public usuarios obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // Actualizar usuario
    public usuarios actualizarUsuario(Long id, usuarios datosActualizados) {

        usuarios usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // ACTUALIZAR CAMPOS — usando tus getters y setters verdaderos
        usuario.setPrimerNombre(datosActualizados.getPrimerNombre());
        usuario.setSegundoNombre(datosActualizados.getSegundoNombre());
        usuario.setPrimerApellido(datosActualizados.getPrimerApellido());
        usuario.setSegundoApellido(datosActualizados.getSegundoApellido());
        usuario.setCorreo(datosActualizados.getCorreo());
        usuario.setClave(datosActualizados.getClave());
        usuario.setDireccion(datosActualizados.getDireccion());
        usuario.setCiudad(datosActualizados.getCiudad());
        //quedo faltando telefono y rol

        return usuarioRepository.save(usuario);
    }

    // Eliminar usuario
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}
