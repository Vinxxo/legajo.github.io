package proyecto_legajo.legajo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import proyecto_legajo.legajo.Entity.libros;

public interface LibrosRepository extends JpaRepository<libros, Long> {

}
