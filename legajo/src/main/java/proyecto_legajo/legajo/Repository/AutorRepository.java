package proyecto_legajo.legajo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import proyecto_legajo.legajo.Entity.autor;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<autor, Integer> {
    @Query("SELECT a FROM autor a WHERE a.NomAutor1 = ?1 AND a.ApeAutor1 = ?2")
    Optional<autor> findByNomAutor1AndApeAutor1(String NomAutor1, String ApeAutor1);
}
