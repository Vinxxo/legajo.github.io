package proyecto_legajo.legajo.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import proyecto_legajo.legajo.Dto.CalificacionDTO;
import proyecto_legajo.legajo.Entity.calificacionLibro;
import proyecto_legajo.legajo.Entity.libros;
import proyecto_legajo.legajo.Entity.usuarios;
import proyecto_legajo.legajo.Repository.CalificacionLibroRepository;
import proyecto_legajo.legajo.Repository.LibrosRepository;

@Service
public class CalificacionLibroService {

    private static final Logger logger = LoggerFactory.getLogger(CalificacionLibroService.class);

    @Autowired
    private CalificacionLibroRepository calificacionRepo;

    @Autowired
    private LibrosRepository librosRepo;

    /**
     * Guardar o actualizar calificación
     * Solo el propietario del libro puede calificarlo
     */
    @Transactional
    public CalificacionDTO guardarCalificacion(int idLibro, byte calificacion, usuarios usuario) {
        
        // Validar calificación
        if (calificacion < 1 || calificacion > 5) {
            throw new IllegalArgumentException("Calificación debe estar entre 1 y 5");
        }

        // Obtener el libro
        libros libro = librosRepo.findById(idLibro)
            .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        // Validar que el usuario sea propietario del libro
        if (libro.getUsuarioPropietario().getIdUsuario() != usuario.getIdUsuario()) {
            throw new RuntimeException("Solo el propietario del libro puede calificarlo");
        }

        // Buscar si ya existe calificación de este usuario
        Optional<calificacionLibro> calificacionExistente = 
            calificacionRepo.findByLibroCalificadoAndUsuarioCalificante(libro, usuario);

        calificacionLibro calificacion_entity;

        if (calificacionExistente.isPresent()) {
            // Actualizar calificación existente
            calificacion_entity = calificacionExistente.get();
            calificacion_entity.setCalificacionLib(calificacion);
            logger.info("✓ Calificación actualizada: libro={}, usuario={}, nuevaCalificacion={}", 
                idLibro, usuario.getCorreo(), calificacion);
        } else {
            // Crear nueva calificación
            calificacion_entity = new calificacionLibro();
            calificacion_entity.setLibroCalificado(libro);
            calificacion_entity.setUsuarioCalificante(usuario);
            calificacion_entity.setCalificacionLib(calificacion);
            calificacion_entity.setActivo(true);
            logger.info("✓ Calificación creada: libro={}, usuario={}, calificacion={}", 
                idLibro, usuario.getCorreo(), calificacion);
        }

        calificacionLibro saved = calificacionRepo.save(calificacion_entity);
        return toDto(saved);
    }

    /**
     * Obtener todas las calificaciones de un libro (historial)
     */
    @Transactional(readOnly = true)
    public List<CalificacionDTO> obtenerCalificacionesDelLibro(int idLibro) {
        libros libro = librosRepo.findById(idLibro)
            .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        List<calificacionLibro> calificaciones = calificacionRepo.findActiveCalificacionesByLibroId(idLibro);
        
        return calificaciones.stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Obtener promedio y cantidad de calificaciones de un libro
     */
    @Transactional(readOnly = true)
    public PromedioCalificacionDTO obtenerPromedioLibro(int idLibro) {
        libros libro = librosRepo.findById(idLibro)
            .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        Optional<Double> promedio = calificacionRepo.getPromedioCalificacion(idLibro);
        long cantidad = calificacionRepo.countByLibroId(idLibro);

        PromedioCalificacionDTO dto = new PromedioCalificacionDTO();
        dto.setPromedio(promedio.orElse(0.0));
        dto.setCantidad(cantidad);
        
        return dto;
    }

    /**
     * Convertir entidad a DTO
     */
    private CalificacionDTO toDto(calificacionLibro cal) {
        CalificacionDTO dto = new CalificacionDTO();
        dto.setIdCalificacionLib(cal.getIdCalificacionLib());
        dto.setCalificacion(cal.getCalificacionLib());
        dto.setFechaCalificacion(cal.getFechaCalificacion());
        
        if (cal.getUsuarioCalificante() != null) {
            dto.setIdUsuarioCalificante(cal.getUsuarioCalificante().getIdUsuario());
            dto.setNombreUsuarioCalificante(
                cal.getUsuarioCalificante().getPrimerNombre() + " " + 
                cal.getUsuarioCalificante().getPrimerApellido()
            );
        }
        
        if (cal.getLibroCalificado() != null) {
            dto.setIdLibro(cal.getLibroCalificado().getIdLibro());
        }
        
        return dto;
    }

    /**
     * DTO para respuesta de promedio
     */
    public static class PromedioCalificacionDTO {
        private double promedio;
        private long cantidad;

        public double getPromedio() {
            return promedio;
        }

        public void setPromedio(double promedio) {
            this.promedio = promedio;
        }

        public long getCantidad() {
            return cantidad;
        }

        public void setCantidad(long cantidad) {
            this.cantidad = cantidad;
        }
    }
}
