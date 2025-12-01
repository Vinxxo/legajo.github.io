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
@Table(name="genero")
public class genero {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idGenero")
    private int idGenero;

    @Column(name="GeneroLib", length=45, nullable=false)
    private String GeneroLib;

    /* Relaciones */

    // Genero - Genero_Libros - Libros
    @ManyToMany(mappedBy = "generos")
    private Set<libros> libros;
    
}