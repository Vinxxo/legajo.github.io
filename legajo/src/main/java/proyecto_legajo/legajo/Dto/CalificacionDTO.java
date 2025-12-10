package proyecto_legajo.legajo.Dto;

import java.time.LocalDateTime;

public class CalificacionDTO {

    private int idCalificacionLib;
    private int idLibro;
    private byte calificacion;
    private LocalDateTime fechaCalificacion;
    
    // Para mostrar en historial
    private int idUsuarioCalificante;
    private String nombreUsuarioCalificante;

    public CalificacionDTO() {}

    public CalificacionDTO(int idCalificacionLib, byte calificacion, LocalDateTime fechaCalificacion, 
                           int idUsuarioCalificante, String nombreUsuarioCalificante) {
        this.idCalificacionLib = idCalificacionLib;
        this.calificacion = calificacion;
        this.fechaCalificacion = fechaCalificacion;
        this.idUsuarioCalificante = idUsuarioCalificante;
        this.nombreUsuarioCalificante = nombreUsuarioCalificante;
    }

    // Getters y Setters
    public int getIdCalificacionLib() {
        return idCalificacionLib;
    }

    public void setIdCalificacionLib(int idCalificacionLib) {
        this.idCalificacionLib = idCalificacionLib;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public byte getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(byte calificacion) {
        if (calificacion < 1 || calificacion > 5) {
            throw new IllegalArgumentException("Calificación debe estar entre 1 y 5");
        }
        this.calificacion = calificacion;
    }

    public LocalDateTime getFechaCalificacion() {
        return fechaCalificacion;
    }

    public void setFechaCalificacion(LocalDateTime fechaCalificacion) {
        this.fechaCalificacion = fechaCalificacion;
    }

    public int getIdUsuarioCalificante() {
        return idUsuarioCalificante;
    }

    public void setIdUsuarioCalificante(int idUsuarioCalificante) {
        this.idUsuarioCalificante = idUsuarioCalificante;
    }

    public String getNombreUsuarioCalificante() {
        return nombreUsuarioCalificante;
    }

    public void setNombreUsuarioCalificante(String nombreUsuarioCalificante) {
        this.nombreUsuarioCalificante = nombreUsuarioCalificante;
    }
}
