package proyecto_legajo.legajo.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class libros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLibro")
    private Long idLibro;

    @Column(name = "TituloLib", length = 100, nullable = false)
    private String titulo;

    @Column(name = "SinopsisLib", length = 400, nullable = false)
    private String sinopsis;

    @Enumerated(EnumType.STRING)
    @Column(name = "EstadoLib", nullable = false)
    private EstadoLibro estado;

    // RELACIÓN con usuarios: MUCHOS libros -> UN usuario
    @ManyToOne
    @JoinColumn(name = "FK_UsuarioPropietario", nullable = false)
    private usuarios usuario;

    // Constructor vacío
    public libros() {}

    // Getters y Setters
    public Long getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Long idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public EstadoLibro getEstado() {
        return estado;
    }

    public void setEstado(EstadoLibro estado) {
        this.estado = estado;
    }

    public usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(usuarios usuario) {
        this.usuario = usuario;
    }
    //
}