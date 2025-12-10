package proyecto_legajo.legajo.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import proyecto_legajo.legajo.Entity.libros;
import proyecto_legajo.legajo.Entity.usuarios;
import proyecto_legajo.legajo.Entity.autor;
import proyecto_legajo.legajo.Entity.genero;
import proyecto_legajo.legajo.Repository.LibrosRepository;
import proyecto_legajo.legajo.Repository.AutorRepository;
import proyecto_legajo.legajo.Repository.GeneroRepository;
import proyecto_legajo.legajo.Dto.LibroResponseDTO;
import proyecto_legajo.legajo.Entity.EstadoLibro;
import proyecto_legajo.legajo.Dto.LibroDTO;

@Service
public class LibrosService {

    private final LibrosRepository repo;
    private final AutorRepository autorRepo;
    private final GeneroRepository generoRepo;

    public LibrosService(LibrosRepository repo, AutorRepository autorRepo, GeneroRepository generoRepo) {
        this.repo = repo;
        this.autorRepo = autorRepo;
        this.generoRepo = generoRepo;
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
        
        // Procesar y guardar autor
        if (dto.getAutor() != null && !dto.getAutor().trim().isEmpty()) {
            Set<autor> autores = new HashSet<>();
            String[] nombresParte = dto.getAutor().trim().split("\\s+");
            if (nombresParte.length >= 2) {
                String nom = nombresParte[0];
                String ape = String.join(" ", java.util.Arrays.copyOfRange(nombresParte, 1, nombresParte.length));
                
                autor autorEntity = autorRepo.findFirstByNomAutor1AndApeAutor1(nom, ape)
                    .orElseGet(() -> {
                        autor nuevoAutor = new autor();
                        nuevoAutor.setNomAutor1(nom);
                        nuevoAutor.setApeAutor1(ape);
                        return autorRepo.save(nuevoAutor);
                    });
                autores.add(autorEntity);
            } else if (nombresParte.length == 1) {
                autor autorEntity = autorRepo.findFirstByNomAutor1AndApeAutor1(nombresParte[0], "")
                    .orElseGet(() -> {
                        autor nuevoAutor = new autor();
                        nuevoAutor.setNomAutor1(nombresParte[0]);
                        nuevoAutor.setApeAutor1("");
                        return autorRepo.save(nuevoAutor);
                    });
                autores.add(autorEntity);
            }
            libro.setAutor(autores);
        }
        
        // Procesar y guardar género
        if (dto.getGenero() != null && !dto.getGenero().trim().isEmpty()) {
            Set<genero> generos = new HashSet<>();
            String[] nombresGenero = dto.getGenero().split(",");
            for (String gen : nombresGenero) {
                String generoNombre = gen.trim();
                genero generoEntity = generoRepo.findFirstByGeneroLib(generoNombre)
                    .orElseGet(() -> {
                        genero nuevoGenero = new genero();
                        nuevoGenero.setGeneroLib(generoNombre);
                        return generoRepo.save(nuevoGenero);
                    });
                generos.add(generoEntity);
            }
            libro.setGeneros(generos);
        }
        
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
            
            // Actualizar autor
            if (dto.getAutor() != null && !dto.getAutor().trim().isEmpty()) {
                Set<autor> autores = new HashSet<>();
                String[] nombresParte = dto.getAutor().trim().split("\\s+");
                if (nombresParte.length >= 2) {
                    String nom = nombresParte[0];
                    String ape = String.join(" ", java.util.Arrays.copyOfRange(nombresParte, 1, nombresParte.length));
                    
                    autor autorEntity = autorRepo.findFirstByNomAutor1AndApeAutor1(nom, ape)
                        .orElseGet(() -> {
                            autor nuevoAutor = new autor();
                            nuevoAutor.setNomAutor1(nom);
                            nuevoAutor.setApeAutor1(ape);
                            return autorRepo.save(nuevoAutor);
                        });
                    autores.add(autorEntity);
                } else if (nombresParte.length == 1) {
                    autor autorEntity = autorRepo.findFirstByNomAutor1AndApeAutor1(nombresParte[0], "")
                        .orElseGet(() -> {
                            autor nuevoAutor = new autor();
                            nuevoAutor.setNomAutor1(nombresParte[0]);
                            nuevoAutor.setApeAutor1("");
                            return autorRepo.save(nuevoAutor);
                        });
                    autores.add(autorEntity);
                }
                libro.setAutor(autores);
            } else {
                libro.setAutor(new HashSet<>());
            }
            
            // Actualizar género
            if (dto.getGenero() != null && !dto.getGenero().trim().isEmpty()) {
                Set<genero> generos = new HashSet<>();
                String[] nombresGenero = dto.getGenero().split(",");
                for (String gen : nombresGenero) {
                    String generoNombre = gen.trim();
                    genero generoEntity = generoRepo.findFirstByGeneroLib(generoNombre)
                        .orElseGet(() -> {
                            genero nuevoGenero = new genero();
                            nuevoGenero.setGeneroLib(generoNombre);
                            return generoRepo.save(nuevoGenero);
                        });
                    generos.add(generoEntity);
                }
                libro.setGeneros(generos);
            } else {
                libro.setGeneros(new HashSet<>());
            }
            
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
        
        // Mapear autores
        if (l.getAutor() != null && !l.getAutor().isEmpty()) {
            String autores = l.getAutor().stream()
                .map(a -> safe(a.getNomAutor1()) + " " + safe(a.getApeAutor1()))
                .collect(Collectors.joining(", "));
            dto.setAutor(autores.trim());
        } else {
            dto.setAutor("");
        }
        
        // Mapear géneros
        if (l.getGeneros() != null && !l.getGeneros().isEmpty()) {
            String generos = l.getGeneros().stream()
                .map(g -> safe(g.getGeneroLib()))
                .collect(Collectors.joining(", "));
            dto.setGenero(generos);
        } else {
            dto.setGenero("");
        }
        
        return dto;
    }

    @Transactional(readOnly = true)
    public List<LibroResponseDTO> obtenerLibrosPorUsuarioId(int usuarioId) {
        List<libros> lista = repo.findByUsuarioPropietario_IdUsuario(usuarioId);
        return lista.stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());
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

        // Obtener lista base del repositorio
        List<libros> lista = repo.buscarPorFiltros(
            empty(usuario) ? null : usuario,
            empty(titulo) ? null : titulo,
            empty(autor) ? null : autor,
            empty(genero) ? null : genero,
            estado
        );

        // Filtrar por autor y género en memoria
        lista = lista.stream()
            .filter(l -> {
                if (autor != null && !autor.isBlank()) {
                    boolean coincideAutor = l.getAutor() != null && l.getAutor().stream()
                        .anyMatch(a -> (safe(a.getNomAutor1()) + " " + safe(a.getApeAutor1()))
                            .toLowerCase().contains(autor.toLowerCase()));
                    if (!coincideAutor) return false;
                }
                if (genero != null && !genero.isBlank()) {
                    boolean coincideGenero = l.getGeneros() != null && l.getGeneros().stream()
                        .anyMatch(g -> safe(g.getGeneroLib()).toLowerCase().contains(genero.toLowerCase()));
                    if (!coincideGenero) return false;
                }
                return true;
            })
            .toList();

        return lista.stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());
    }

    private LibroResponseDTO mapToDto(libros l) {
        LibroResponseDTO dto = new LibroResponseDTO();

        // ID
        dto.setIdLibro(l.getIdLibro());

        // ID DEL USUARIO PROPIETARIO
        if (l.getUsuarioPropietario() != null) {
            dto.setUsuarioPropietarioId(l.getUsuarioPropietario().getIdUsuario());
        }

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

        // DESCRIPCION (SINOPSIS)
        dto.setDescripcion(safe(l.getSinopsisLib()));

        // AUTOR
        if (l.getAutor() != null && !l.getAutor().isEmpty()) {
            String autores = l.getAutor().stream()
                .map(a -> safe(a.getNomAutor1()) + " " + safe(a.getApeAutor1()))
                .collect(Collectors.joining(", "));
            dto.setAutor(autores.trim());
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
