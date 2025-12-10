package proyecto_legajo.legajo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import proyecto_legajo.legajo.Entity.calificacionLibro;
import proyecto_legajo.legajo.Entity.libros;
import proyecto_legajo.legajo.Entity.usuarios;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalificacionLibroRepository extends JpaRepository<calificacionLibro, Integer> {

    // Buscar calificación por libro y usuario
    Optional<calificacionLibro> findByLibroCalificadoAndUsuarioCalificante(libros libro, usuarios usuario);

    // Obtener todas las calificaciones de un libro
    List<calificacionLibro> findByLibroCalificado(libros libro);

    // Obtener promedio de calificación de un libro
    @Query("SELECT AVG(c.CalificacionLib) FROM calificacionLibro c WHERE c.libroCalificado.idLibro = :idLibro AND c.Activo = true")
    Optional<Double> getPromedioCalificacion(@Param("idLibro") int idLibro);

    // Contar calificaciones activas de un libro
    @Query("SELECT COUNT(c) FROM calificacionLibro c WHERE c.libroCalificado.idLibro = :idLibro AND c.Activo = true")
    long countByLibroId(@Param("idLibro") int idLibro);

    // Obtener calificaciones activas de un libro ordenadas por fecha descendente
    @Query("SELECT c FROM calificacionLibro c WHERE c.libroCalificado.idLibro = :idLibro AND c.Activo = true ORDER BY c.FechaCalificacion DESC")
    List<calificacionLibro> findActiveCalificacionesByLibroId(@Param("idLibro") int idLibro);
}
