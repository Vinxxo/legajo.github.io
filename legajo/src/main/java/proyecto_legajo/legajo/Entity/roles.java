package proyecto_legajo.legajo.Entity;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name  = "idRol")
    private int idRol;

    @Column(name="rol", length=15, nullable=false)
    private String rol;

    /* Relaciones */

    // Roles - Usuarios
    @OneToMany(mappedBy = "rol")
    private List<usuarios> usuarios;
}
