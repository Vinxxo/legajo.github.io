package proyecto_legajo.legajo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proyecto_legajo.legajo.Entity.usuarios;

@Repository
public interface usuarioRepository extends JpaRepository<usuarios, Long> {
Optional<usuarios> findByCorreo(String correo);

}
