package proyecto_legajo.legajo.Repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import proyecto_legajo.legajo.Entity.reportesUsuario;
import java.util.List;

@Repository
public interface reportesUsuarioRepository extends ListCrudRepository<reportesUsuario, Integer> {
    List<reportesUsuario> findByUsuarioReportante_IdUsuario(Integer idUsuario);
}
