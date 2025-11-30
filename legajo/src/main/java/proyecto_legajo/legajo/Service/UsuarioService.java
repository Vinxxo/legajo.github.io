package proyecto_legajo.legajo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proyecto_legajo.legajo.Entity.usuarios;
import proyecto_legajo.legajo.Repository.usuarioRepository;
import proyecto_legajo.legajo.Dto.UsuarioDTO;
import proyecto_legajo.legajo.Dto.LibroDTO;
import proyecto_legajo.legajo.Entity.libros;
import java.util.stream.Collectors;
import java.util.ArrayList;

import java.util.List;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

@Service
public class UsuarioService {

    @Autowired
    private usuarioRepository usuarioRepository;
    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    // Crear usuario
    public usuarios crearUsuario(usuarios usuario) {
        // Asegurar campos obligatorios antes de guardar: clave y rol por defecto
        if (usuario.getClave() == null || usuario.getClave().isEmpty()) {
            usuario.setClave("changeme");
        }
        // Encode password before saving
        if (usuario.getClave() != null) {
            usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        }
        if (usuario.getRol() == null) {
            // Asignar rol por defecto con id 2 (Usuario). Se asume que existe en la BD.
            proyecto_legajo.legajo.Entity.roles rolDef = new proyecto_legajo.legajo.Entity.roles();
            rolDef.setIdRol(2L);
            usuario.setRol(rolDef);
        }
        return usuarioRepository.save(usuario);
    }

    // Convert entity -> DTO
    public UsuarioDTO toDto(usuarios u) {
        if (u == null) return null;
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(u.getIdUsuario());
        dto.setPrimerNombre(u.getPrimerNombre());
        dto.setSegundoNombre(u.getSegundoNombre());
        dto.setPrimerApellido(u.getPrimerApellido());
        dto.setSegundoApellido(u.getSegundoApellido());
        dto.setCorreo(u.getCorreo());
        dto.setDireccion(u.getDireccion());
        dto.setCiudad(u.getCiudad());
        dto.setTelefono(u.getTelefono());
        // Map libros to LibroDTOs if present
        if (u.getLibros() != null) {
            dto.setLibros(u.getLibros().stream().map(this::libroToDto).collect(Collectors.toList()));
        } else {
            dto.setLibros(new ArrayList<>());
        }
        return dto;
    }

    // Convert DTO -> entity (nota: no setea la clave ni roles automáticamente)
    public usuarios fromDto(UsuarioDTO dto) {
        if (dto == null) return null;
        usuarios u = new usuarios();
        u.setIdUsuario(dto.getIdUsuario());
        u.setPrimerNombre(dto.getPrimerNombre());
        u.setSegundoNombre(dto.getSegundoNombre());
        u.setPrimerApellido(dto.getPrimerApellido());
        u.setSegundoApellido(dto.getSegundoApellido());
        u.setCorreo(dto.getCorreo());
        u.setDireccion(dto.getDireccion());
        u.setCiudad(dto.getCiudad());
        u.setTelefono(dto.getTelefono());
        // Mapear la clave si viene en el DTO para que pueda ser codificada al guardar
        try {
            Method m = dto.getClass().getMethod("getClave");
            if (m != null) {
                Object val = m.invoke(dto);
                if (val != null) {
                    u.setClave(val.toString());
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            // DTO no define getClave() o no accesible, ignorar
        }
        // Note: fromDto does not set libros; handle libros separately if needed
        return u;
    }

    // Helper to map libro entity -> DTO
    public LibroDTO libroToDto(libros l) {
        if (l == null) return null;
        LibroDTO d = new LibroDTO();
        d.setIdLibro(l.getIdLibro());
        d.setTitulo(l.getTitulo());
        d.setSinopsis(l.getSinopsis());
        d.setEstado(l.getEstado() == null ? null : l.getEstado().name());
        return d;
    }

    // Listar todos los usuarios y convertir a DTO dentro de una transacción
    @Transactional(readOnly = true)
    public List<proyecto_legajo.legajo.Dto.UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
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
        if (datosActualizados.getPrimerNombre() != null) {
            usuario.setPrimerNombre(datosActualizados.getPrimerNombre());
        }
        if (datosActualizados.getSegundoNombre() != null) {
            usuario.setSegundoNombre(datosActualizados.getSegundoNombre());
        }
        if (datosActualizados.getPrimerApellido() != null) {
            usuario.setPrimerApellido(datosActualizados.getPrimerApellido());
        }
        if (datosActualizados.getSegundoApellido() != null) {
            usuario.setSegundoApellido(datosActualizados.getSegundoApellido());
        }
        if (datosActualizados.getCorreo() != null) {
            usuario.setCorreo(datosActualizados.getCorreo());
        }
        // No sobrescribir la clave si no viene en el DTO
        if (datosActualizados.getClave() != null) {
            usuario.setClave(passwordEncoder.encode(datosActualizados.getClave()));
        }
        if (datosActualizados.getDireccion() != null) {
            usuario.setDireccion(datosActualizados.getDireccion());
        }
        if (datosActualizados.getCiudad() != null) {
            usuario.setCiudad(datosActualizados.getCiudad());
        }
        if (datosActualizados.getTelefono() != null) {
            usuario.setTelefono(datosActualizados.getTelefono());
        }
        if (datosActualizados.getRol() != null) {
            usuario.setRol(datosActualizados.getRol());
        }

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
