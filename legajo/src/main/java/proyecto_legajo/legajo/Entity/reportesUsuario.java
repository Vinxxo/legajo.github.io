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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "reportesUsuario")
public class reportesUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idReportesUsu")
    private int idReportesUsu;

    @Column(name = "Motivo", length = 100, nullable = false)
    private String Motivo;

    @Lob
    @Column(name = "Descripcion", columnDefinition = "TEXT")
    private String Descripcion;

    @Column(name = "FechaReporte", insertable = false, updatable = false)
    private LocalDateTime FechaReporte;

    @Enumerated(EnumType.STRING)
    @Column(name = "Estado")
    private EstadoReporte Estado = EstadoReporte.pendiente;

    @Column(name = "Activo", nullable = false)
    private boolean Activo = true;

    /* Relaciones */

    // ReportesUsuario - Usuarios (Reportante)
    @ManyToOne
    @JoinColumn(name = "FK_idUsuarioReportante")
    private usuarios usuarioReportante;
    
    // ReportesUsuario - Usuarios (Reportado)

    @ManyToOne
    @JoinColumn(name = "FK_idUsuarioReportado")
    private usuarios usuarioReportado;

}
