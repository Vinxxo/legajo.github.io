package proyecto_legajo.legajo.Entity;

import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="autor")
public class autor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAutor")
    private int idAutor;

    @Column(name="NomAutor1", length=30, nullable=false)
    private String NomAutor1;

    @Column(name="NomAutor2", length=30)
    private String NomAutor2;

    @Column(name="ApeAutor1", length=30, nullable=false)
    private String ApeAutor1;

    @Column(name="ApeAutor2", length=30)
    private String ApeAutor2;

    @Column(name="ApodoAutor", length=50)
    private String ApodoAutor;

    /* Relaciones */

    // Autor - Autor_Libros - Libros
    @ManyToMany(mappedBy = "autor")
    private Set<libros> libros;
}