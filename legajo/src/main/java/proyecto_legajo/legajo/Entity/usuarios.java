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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(name = "NomUsu1", length = 20, nullable = false)
    private String primerNombre;

    @Column(name = "NomUsu2", length = 20)
    private String segundoNombre;

    @Column(name = "ApeUsu1", length = 20, nullable = false)
    private String primerApellido;

    @Column(name = "ApeUsu2", length = 20)
    private String segundoApellido;

    @Column(name = "CorreoUsu", length = 50, nullable = false, unique = true)
    private String correo;

    @Column(name = "Clave", length = 8, nullable = false)
    private String clave;

    @Column(name = "DireccionUsu", length = 50)
    private String direccion;

    @Column(name = "CiudadUsu", length = 15)
    private String ciudad;

    @Column(name = "TelefonoUsu", length = 20)
    private long telefono;

    // Constructor

    public usuarios(Long id, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
            String correo, String clave, String direccion, String ciudad, long telefono) {
        this.idUsuario = id;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.correo = correo;
        this.clave = clave;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.telefono = telefono;
    }

    // Getters y Setters

    public Long getId() {
        return idUsuario;
    }

    public void setId(Long id) {
        this.idUsuario = id;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    // Relaciones
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<libros> libros;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private roles roles;
}