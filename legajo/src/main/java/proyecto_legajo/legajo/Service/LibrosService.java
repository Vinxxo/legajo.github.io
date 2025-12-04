package proyecto_legajo.legajo.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import proyecto_legajo.legajo.Entity.libros;
import proyecto_legajo.legajo.Entity.usuarios;
import proyecto_legajo.legajo.Repository.LibrosRepository;
import proyecto_legajo.legajo.Dto.LibroResponseDTO;
import proyecto_legajo.legajo.Entity.EstadoLibro;
import proyecto_legajo.legajo.Dto.LibroDTO;

@Service
public class LibrosService {

    private final LibrosRepository repo;

    public LibrosService(LibrosRepository repo) {
        this.repo = repo;
    }

    // Obtener libro por ID
    @Transactional(readOnly = true)
    public LibroDTO obtenerPorId(int id) {
        return repo.findById(id).map(this::mapToLibroDTO).orElse(null);
    }

    // Crear libro
    @Transactional
    public LibroDTO crearLibro(LibroDTO dto, usuarios usuario) {
        libros libro = new libros();
        libro.setTituloLib(dto.getTitulo());
        libro.setSinopsisLib(dto.getSinopsis());
        libro.setEstadoLib(EstadoLibro.valueOf(dto.getEstado()));
        // Usar imagen proporcionada o una por defecto
        String urlImagen = dto.getUrlImagen() != null && !dto.getUrlImagen().trim().isEmpty() 
            ? dto.getUrlImagen() 
            : "/imgs/default-book.jpg";
        libro.setUrlImagen(urlImagen);
        libro.setActivo(true);
        // Asociar el usuario propietario
        libro.setUsuarioPropietario(usuario);
        repo.save(libro);
        return mapToLibroDTO(libro);
    }

    // Actualizar libro
    @Transactional
    public LibroDTO actualizarLibro(int id, LibroDTO dto) {
        return repo.findById(id).map(libro -> {
            libro.setTituloLib(dto.getTitulo());
            libro.setSinopsisLib(dto.getSinopsis());
            libro.setUrlImagen(dto.getUrlImagen());
            libro.setEstadoLib(EstadoLibro.valueOf(dto.getEstado()));
            repo.save(libro);
            return mapToLibroDTO(libro);
        }).orElse(null);
    }

    // Eliminar libro
    @Transactional
    public boolean eliminarLibro(int id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }

    // Mapear entidad a DTO para CRUD
    private LibroDTO mapToLibroDTO(libros l) {
        LibroDTO dto = new LibroDTO();
        dto.setIdLibro(l.getIdLibro());
        dto.setTitulo(l.getTituloLib());
        dto.setSinopsis(l.getSinopsisLib());
        dto.setEstado(l.getEstadoLib() != null ? l.getEstadoLib().name() : "");
        dto.setUrlImagen(l.getUrlImagen());
        return dto;
    }

    @Transactional(readOnly = true)
    public List<LibroResponseDTO> buscarPorFiltros(
            String usuario,
            String titulo,
            String autor,
            String genero,
            String estadoStr
    ) {
        EstadoLibro estado = null;
        if (estadoStr != null && !estadoStr.isBlank()) {
            try {
                estado = EstadoLibro.valueOf(estadoStr);
            } catch (Exception ex) {
                estado = null;
            }
        }

        List<libros> lista = repo.buscarPorFiltros(
            empty(usuario) ? null : usuario,
            empty(titulo) ? null : titulo,
            empty(autor) ? null : autor,
            empty(genero) ? null : genero,
            estado
        );

        return lista.stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());
    }

    private LibroResponseDTO mapToDto(libros l) {
        LibroResponseDTO dto = new LibroResponseDTO();

        // ID
        dto.setIdLibro(l.getIdLibro());

        // USUARIO
        if (l.getUsuarioPropietario() != null) {
            String nombre = safe(l.getUsuarioPropietario().getPrimerNombre());
            String apellido = safe(l.getUsuarioPropietario().getPrimerApellido());
            dto.setUsuario((nombre + " " + apellido).trim());
        } else {
            dto.setUsuario("");
        }

        // TITULO
        dto.setTitulo(safe(l.getTituloLib()));

        // AUTOR
        if (l.getAutor() != null && !l.getAutor().isEmpty()) {
            String autores = l.getAutor().stream()
                .map(a -> safe(a.getNomAutor1()) + " " + safe(a.getApeAutor1()))
                .collect(Collectors.joining(", "));
            dto.setAutor(autores);
        } else {
            dto.setAutor("");
        }

        // GÉNERO
        if (l.getGeneros() != null && !l.getGeneros().isEmpty()) {
            String generos = l.getGeneros().stream()
                .map(g -> safe(g.getGeneroLib()))
                .collect(Collectors.joining(", "));
            dto.setGenero(generos);
        } else {
            dto.setGenero("");
        }

        // ESTADO
        dto.setEstado(l.getEstadoLib() != null ? l.getEstadoLib().name() : "");

        // URL IMAGEN
        dto.setUrlImagen(l.getUrlImagen());

        return dto;
    }

    private boolean empty(String s) {
        return s == null || s.isBlank();
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }
}
