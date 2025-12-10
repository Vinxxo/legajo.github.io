package proyecto_legajo.legajo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import proyecto_legajo.legajo.Entity.libros;
import proyecto_legajo.legajo.Entity.EstadoLibro;

public interface LibrosRepository extends JpaRepository<libros, Integer> {

    // Obtener libros por ID del usuario propietario
    List<libros> findByUsuarioPropietario_IdUsuario(int usuarioId);

    @Query("""
        SELECT DISTINCT l FROM libros l
        LEFT JOIN FETCH l.usuarioPropietario
        LEFT JOIN FETCH l.autor
        WHERE (:usuario IS NULL OR LOWER(CONCAT(COALESCE(l.usuarioPropietario.primerNombre, ''), ' ', COALESCE(l.usuarioPropietario.primerApellido, ''))) LIKE LOWER(CONCAT('%', :usuario, '%')))
          AND (:titulo IS NULL OR LOWER(COALESCE(l.TituloLib, '')) LIKE LOWER(CONCAT('%', :titulo, '%')))
          AND (:estado IS NULL OR l.EstadoLib = :estado)
        ORDER BY l.idLibro
    """)
    List<libros> buscarPorFiltros(
        @Param("usuario") String usuario,
        @Param("titulo") String titulo,
        @Param("autor") String autor,
        @Param("genero") String genero,
        @Param("estado") EstadoLibro estado
    );

}
