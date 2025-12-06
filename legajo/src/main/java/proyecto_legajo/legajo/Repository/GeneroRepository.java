package proyecto_legajo.legajo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import proyecto_legajo.legajo.Entity.genero;
import java.util.Optional;

@Repository
public interface GeneroRepository extends JpaRepository<genero, Integer> {
    @Query("SELECT g FROM genero g WHERE g.GeneroLib = ?1")
    Optional<genero> findByGeneroLib(String GeneroLib);
}
