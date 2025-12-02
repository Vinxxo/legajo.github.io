package proyecto_legajo.legajo.Entity;

import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private int idUsuario;

    @Column(name = "NomUsu1", length = 30, nullable = false)
    private String primerNombre;

    @Column(name = "NomUsu2", length = 30)
    private String segundoNombre;

    @Column(name = "ApeUsu1", length = 30, nullable = false)
    private String primerApellido;

    @Column(name = "ApeUsu2", length = 30)
    private String segundoApellido;

    @Column(name = "CorreoUsu", length = 50, nullable = false, unique = true)
    private String correo;

    @Column(name = "Clave", length = 100, nullable = false)
    private String clave;

    @Column(name = "DireccionUsu", length = 50, nullable = false)
    private String direccion;

    @Column(name = "CiudadUsu", length = 20, nullable = false)
    private String ciudad;

    @Column(name = "TelefonoUsu", nullable = false)
    private Long telefono;

    @Column(name = "Activo", nullable = false)
    private Boolean activo = true;

    /* Relaciones */

    // Usuarios - Libros
    @OneToMany(mappedBy = "usuarioPropietario", cascade = CascadeType.ALL)
    private List<libros> libros;

    // Usuarios - Roles
    @ManyToOne
    @JoinColumn(name = "FK_rolUsuario")
    private roles rol;

    // Usuarios - CalificaciónLibro
    @OneToMany(mappedBy = "usuarioCalificante")
    private List<calificacionLibro> calificacionLibros;

    // Usuarios - ReportesUsuarios (Reportante)
    @OneToMany(mappedBy = "usuarioReportante")
    private List<reportesUsuario> reporteReportante;

    // Usuarios - ReportesUsuarios (Reportado)
    @OneToMany(mappedBy = "usuarioReportado")
    private List<reportesUsuario> reporteReportado;

    // Usuarios - Intercambios (Solicitante)
    @OneToMany(mappedBy = "usuarioSolicitante")
    private List<intercambios> intercambioSolicitante;

    // Usuarios - Intercambios (Receptor)
    @OneToMany(mappedBy = "usuarioReceptor")
    private List<intercambios> intercambioReceptor;

}