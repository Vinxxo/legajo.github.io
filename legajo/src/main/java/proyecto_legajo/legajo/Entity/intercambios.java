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
import jakarta.persistence.PrePersist;
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

    @Column(name = "FechaSolicitud", insertable = true, updatable = false)
    private LocalDateTime fechaSolicitud;

    @Column(name = "FechaConfirmacion")
    private LocalDateTime fechaConfirmacion;

    @Column(name = "FechaCompletado")
    private LocalDateTime fechaCompletado;

    @Enumerated(EnumType.STRING)
    @Column(name = "EstadoInter")
    private EstadoIntercambio estadoInter = EstadoIntercambio.pendiente;

    @Column(name = "Activo", nullable = false)
    private boolean activo = true;

    // Hook para fijar fechaSolicitud automáticamente al crear
    @PrePersist
    protected void onCreate() {
        if (this.fechaSolicitud == null) {
            this.fechaSolicitud = LocalDateTime.now();
        }
    }

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
