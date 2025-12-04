package proyecto_legajo.legajo.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import proyecto_legajo.legajo.Entity.libros;
import proyecto_legajo.legajo.Repository.LibrosRepository;
import proyecto_legajo.legajo.Dto.LibroResponseDTO;
import proyecto_legajo.legajo.Entity.EstadoLibro;

@Service
public class LibrosService {

    private final LibrosRepository repo;

    public LibrosService(LibrosRepository repo) {
        this.repo = repo;
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

        return dto;
    }

    private boolean empty(String s) {
        return s == null || s.isBlank();
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }
}
