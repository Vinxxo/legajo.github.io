package proyecto_legajo.legajo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import proyecto_legajo.legajo.Entity.libros;
import proyecto_legajo.legajo.Entity.EstadoLibro; // importa tu enum

public interface LibrosRepository extends JpaRepository<libros, Integer> {

    @Query("""
    SELECT DISTINCT l FROM libros l
    LEFT JOIN l.usuarioPropietario u
    LEFT JOIN l.autor a
    LEFT JOIN l.generos g
    WHERE (:usuario IS NULL OR CONCAT(u.primerNombre, ' ', u.primerApellido) LIKE CONCAT('%', :usuario, '%'))
      AND (:titulo IS NULL OR l.TituloLib LIKE CONCAT('%', :titulo, '%'))
      AND (:autor IS NULL OR CONCAT(a.NomAutor1, ' ', a.ApeAutor1) LIKE CONCAT('%', :autor, '%'))
      AND (:genero IS NULL OR g.GeneroLib LIKE CONCAT('%', :genero, '%'))
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
