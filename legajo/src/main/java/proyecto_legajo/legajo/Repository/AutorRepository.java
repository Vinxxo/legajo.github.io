package proyecto_legajo.legajo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import proyecto_legajo.legajo.Entity.autor;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<autor, Integer> {
    @Query(value = "SELECT * FROM autor WHERE NomAutor1 = :nomAutor1 AND ApeAutor1 = :apeAutor1 ORDER BY idAutor LIMIT 1", nativeQuery = true)
    Optional<autor> findFirstByNomAutor1AndApeAutor1(String nomAutor1, String apeAutor1);
}
