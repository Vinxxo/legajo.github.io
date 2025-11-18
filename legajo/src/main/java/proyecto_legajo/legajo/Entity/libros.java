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
@Table(name="libros")
public class libros {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idLibro;

    @Size(max = 100)
    @Column(name = "TituloLib", length = 100, nullable = false)
    private String tituloLib;

    @Size(max = 400)
    @Column(name = "SinopsisLib", length = 400, nullable = false)
    private String sinopsisLib;

    @Enumerated(EnumType.STRING)
    @Column(name = "EstadoLib", nullable = false)
    private EnumType estadoLib;

    //Constructor
    
    public libros(int idLibro, @Size(max = 100) String tituloLib, @Size(max = 400) String sinopsisLib,
            EnumType estadoLib) {
        this.idLibro = idLibro;
        this.tituloLib = tituloLib;
        this.sinopsisLib = sinopsisLib;
        this.estadoLib = estadoLib;
    }

    //Getters y Setters

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public String getTituloLib() {
        return tituloLib;
    }

    public void setTituloLib(String tituloLib) {
        this.tituloLib = tituloLib;
    }

    public String getSinopsisLib() {
        return sinopsisLib;
    }

    public void setSinopsisLib(String sinopsisLib) {
        this.sinopsisLib = sinopsisLib;
    }

    public EnumType getEstadoLib() {
        return estadoLib;
    }

    public void setEstadoLib(EnumType estadoLib) {
        this.estadoLib = estadoLib;
    }

    // Relaciones
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private usuarios usuarios;

    @ManyToMany
    @JoinTable(
        name = "autor_libros",
        joinColumns = @JoinColumn(name = "libros_id"),
        inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private Set<autor> autor = new HashSet<>();
}
