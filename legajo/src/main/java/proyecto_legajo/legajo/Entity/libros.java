package proyecto_legajo.legajo.Entity;

import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class libros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLibro")
    private int idLibro;

    @Column(name = "TituloLib", length = 100, nullable = false)
    private String TituloLib;

    @Lob
    @Column(name = "SinopsisLib", nullable = false)
    private String SinopsisLib;

    @Enumerated(EnumType.STRING)
    @Column(name = "EstadoLib", nullable = false)
    private EstadoLibro EstadoLib;

    @Column(name = "UrlImagen", length = 255, nullable = false)
    private String UrlImagen;

    @Column(name = "Activo")
    private boolean Activo = true;

    /* Relaciones */

    // Libros - Genero_Libros - Genero
    @ManyToMany
    @JoinTable(
        name = "genero_libros",
        joinColumns = @JoinColumn(name = "FK_idLibro"),
        inverseJoinColumns = @JoinColumn(name = "FK_idGenero")
    )
    private Set<genero> generos;

    // Libros - Autor_Libros - Autor
    @ManyToMany (mappedBy = "libros")
    private Set<autor> autor;

    // Libros - Usuarios
    @ManyToOne
    @JoinTable(name = "FK_UsuarioPropietario")
    private usuarios usuarioPropietario;
    
    // Libros - CalificacionLibro
    @OneToMany(mappedBy = "libroCalificado")
    private calificacionLibro calificacionLibCalificado;
    
    // Libros - Intercambios (Solicitado)
    @OneToMany(mappedBy = "libroSolicitado")
    private intercambios intercambioSolicitado;

    // Libros - Intercambios (Cambio)
    @OneToMany(mappedBy = "libroCambio")
    private intercambios intercambiosCambio;

}