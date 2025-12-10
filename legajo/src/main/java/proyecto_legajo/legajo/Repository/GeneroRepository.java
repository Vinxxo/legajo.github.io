package proyecto_legajo.legajo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import proyecto_legajo.legajo.Entity.genero;
import java.util.Optional;

@Repository
public interface GeneroRepository extends JpaRepository<genero, Integer> {
    @Query(value = "SELECT * FROM genero WHERE GeneroLib = :generoLib ORDER BY idGenero LIMIT 1", nativeQuery = true)
    Optional<genero> findFirstByGeneroLib(String generoLib);
}
