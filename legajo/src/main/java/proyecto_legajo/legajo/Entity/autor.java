package proyecto_legajo.legajo.Entity;

import java.util.List;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name="autor")
public class autor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAutor;

    @Column(name="NomAutor1", length=20, nullable=false)
    private String NomAutor1;

    @Column(name="NomAutor2", length=20)
    private String NomAutor2;

    @Column(name="ApeAutor1", length=20, nullable=false)
    private String ApeAutor1;

    @Column(name="ApeAutor2", length=20)
    private String ApeAutor2;

    @Column(name="ApodoAutor", length=40)
    private String ApodoAutor;

    // Constructor

    public autor(int id, String nomAutor1, String nomAutor2, String apeAutor1, String apeAutor2, String apodoAutor) {
        this.idAutor = id;
        NomAutor1 = nomAutor1;
        NomAutor2 = nomAutor2;
        ApeAutor1 = apeAutor1;
        ApeAutor2 = apeAutor2;
        ApodoAutor = apodoAutor;
    }

    // Getters y Setters

    public int getId() {
        return idAutor;
    }

    public void setId(int id) {
        this.idAutor = id;
    }

    public String getNomAutor1() {
        return NomAutor1;
    }

    public void setNomAutor1(String nomAutor1) {
        NomAutor1 = nomAutor1;
    }

    public String getNomAutor2() {
        return NomAutor2;
    }

    public void setNomAutor2(String nomAutor2) {
        NomAutor2 = nomAutor2;
    }

    public String getApeAutor1() {
        return ApeAutor1;
    }

    public void setApeAutor1(String apeAutor1) {
        ApeAutor1 = apeAutor1;
    }

    public String getApeAutor2() {
        return ApeAutor2;
    }

    public void setApeAutor2(String apeAutor2) {
        ApeAutor2 = apeAutor2;
    }

    public String getApodoAutor() {
        return ApodoAutor;
    }

    public void setApodoAutor(String apodoAutor) {
        ApodoAutor = apodoAutor;
    }

    // Relaciones
    
}