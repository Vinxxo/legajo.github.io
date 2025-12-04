package proyecto_legajo.legajo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import proyecto_legajo.legajo.Entity.libros;
import proyecto_legajo.legajo.Entity.EstadoLibro;

public interface LibrosRepository extends JpaRepository<libros, Integer> {

    @Query("""
        SELECT DISTINCT l FROM libros l
        LEFT JOIN FETCH l.usuarioPropietario u
        LEFT JOIN FETCH l.autor a
        LEFT JOIN FETCH l.generos g
        WHERE (:usuario IS NULL OR LOWER(CONCAT(COALESCE(u.primerNombre, ''), ' ', COALESCE(u.primerApellido, ''))) LIKE LOWER(CONCAT('%', :usuario, '%')))
          AND (:titulo IS NULL OR LOWER(COALESCE(l.TituloLib, '')) LIKE LOWER(CONCAT('%', :titulo, '%')))
          AND (:autor IS NULL OR LOWER(CONCAT(COALESCE(a.NomAutor1, ''), ' ', COALESCE(a.ApeAutor1, ''))) LIKE LOWER(CONCAT('%', :autor, '%')))
          AND (:genero IS NULL OR LOWER(COALESCE(g.GeneroLib, '')) LIKE LOWER(CONCAT('%', :genero, '%')))
          AND (:estado IS NULL OR l.EstadoLib = :estado)
    """)
    List<libros> buscarPorFiltros(
        @Param("usuario") String usuario,
        @Param("titulo") String titulo,
        @Param("autor") String autor,
        @Param("genero") String genero,
        @Param("estado") EstadoLibro estado
    );

}
