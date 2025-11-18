package proyecto_legajo.legajo.Entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="genero")
public class genero {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idGenero;

    @Column(name="GeneroLib", length=40, nullable=false)
    private String GeneroLib;

    // Constructor

    public genero(int idGenero, String generoLib) {
        this.idGenero = idGenero;
        GeneroLib = generoLib;
    }

    // Getters y Setters

    public int getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(int idGenero) {
        this.idGenero = idGenero;
    }

    public String getGeneroLib() {
        return GeneroLib;
    }

    public void setGeneroLib(String generoLib) {
        GeneroLib = generoLib;
    }

    // Relaciones
    @ManyToMany
    @JoinTable(
        name = "genero_libros",
        joinColumns = @JoinColumn(name = "genero_id"),
        inverseJoinColumns = @JoinColumn(name = "libros_id")
    )
    private Set<libros> libros = new HashSet<>();
    
}