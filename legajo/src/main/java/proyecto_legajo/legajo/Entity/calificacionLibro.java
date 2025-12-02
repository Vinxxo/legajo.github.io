package proyecto_legajo.legajo.Entity;

import java.time.LocalDateTime;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "calificacionLibro")
public class calificacionLibro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCalificacionLib")
    private int idCalificacionLib;

    @Column(name = "CalificacionLib")
    @Min(1)
    @Max(5)
    private byte CalificacionLib;

    @Lob
    @Column(name = "Comentario", columnDefinition = "TEXT")
    private String Comentario;

    @Column(name = "FechaCalificacion", insertable = false, updatable = false)
    private LocalDateTime FechaCalificacion;

    @Column(name = "Activo", nullable = false)
    private boolean Activo = true;

    /* Relaciones */

    // CalificacionLibro - Usuarios (Calificante)
    @ManyToOne
    @JoinColumn(name = "FK_UsuarioCalificante")
    private usuarios usuarioCalificante;

    // CalificacionLibro - Libros (Calificado)
    @ManyToOne
    @JoinColumn(name = "FK_LibroCalificado")
    private libros libroCalificado;
}
