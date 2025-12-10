package proyecto_legajo.legajo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import proyecto_legajo.legajo.Entity.intercambios;
import proyecto_legajo.legajo.Entity.EstadoIntercambio;
import java.util.List;

@Repository
public interface IntercambiosRepository extends JpaRepository<intercambios, Integer> {
    @Query("SELECT i FROM intercambios i WHERE i.usuarioReceptor.idUsuario = ?1 AND i.EstadoInter = ?2")
    List<intercambios> findByUsuarioReceptorIdUsuarioAndEstadoInter(int receptorId, EstadoIntercambio estado);
}
