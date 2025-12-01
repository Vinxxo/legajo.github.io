package proyecto_legajo.legajo.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "intercambios")
public class intercambios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idIntercambio")
    private int idIntercambio;

    @Column(name = "FechaSolicitud", insertable = false, updatable = false)
    private LocalDateTime FechaSolicitud;

    @Column(name = "FechaConfirmacion")
    private LocalDateTime FechaConfirmacion;

    @Column(name = "FechaCompletado")
    private LocalDateTime FechaCompletado;

    @Enumerated(EnumType.STRING)
    @Column(name = "EstadoInter")
    private EstadoIntercambio EstadoInter = EstadoIntercambio.pendiente;

    @Column(name = "Activo")
    private boolean Activo = true;

    /* Relaciones */

    // Intercambios - Usuarios (Solicitante)
    @ManyToOne
    @JoinColumn(name = "FK_UsuarioSolicitante")
    private usuarios usuarioSolicitante;

    // Intercambios - Usuarios (Receptor)
    @ManyToOne
    @JoinColumn(name = "FK_UsuarioReceptor")
    private usuarios usuarioReceptor;

    // Intercambios - Libros (Solicitado)
    @ManyToOne
    @JoinColumn(name = "FK_LibroSolicitado")
    private libros libroSolicitado;

    // Intercambios - Libros (Cambio)
    @ManyToOne
    @JoinColumn(name = "FK_LibroCambio")
    private libros libroCambio;


}
