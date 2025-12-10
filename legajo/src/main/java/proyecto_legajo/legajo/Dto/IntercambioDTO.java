package proyecto_legajo.legajo.Dto;

import java.time.LocalDateTime;

public class IntercambioDTO {
    private int idIntercambio;
    private int idSolicitante;
    private String nombreSolicitante;
    private int idReceptor;
    private String nombreReceptor;
    private int idLibroSolicitado;
    private String tituloSolicitado;
    private Integer idLibroCambio;
    private String tituloLibroCambio;
    private String estadoInter;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaConfirmacion;

    public IntercambioDTO() {}

    // getters & setters
    public int getIdIntercambio() { return idIntercambio; }
    public void setIdIntercambio(int idIntercambio) { this.idIntercambio = idIntercambio; }
    public int getIdSolicitante() { return idSolicitante; }
    public void setIdSolicitante(int idSolicitante) { this.idSolicitante = idSolicitante; }
    public String getNombreSolicitante() { return nombreSolicitante; }
    public void setNombreSolicitante(String nombreSolicitante) { this.nombreSolicitante = nombreSolicitante; }
    public int getIdReceptor() { return idReceptor; }
    public void setIdReceptor(int idReceptor) { this.idReceptor = idReceptor; }
    public String getNombreReceptor() { return nombreReceptor; }
    public void setNombreReceptor(String nombreReceptor) { this.nombreReceptor = nombreReceptor; }
    public int getIdLibroSolicitado() { return idLibroSolicitado; }
    public void setIdLibroSolicitado(int idLibroSolicitado) { this.idLibroSolicitado = idLibroSolicitado; }
    public String getTituloSolicitado() { return tituloSolicitado; }
    public void setTituloSolicitado(String tituloSolicitado) { this.tituloSolicitado = tituloSolicitado; }
    public Integer getIdLibroCambio() { return idLibroCambio; }
    public void setIdLibroCambio(Integer idLibroCambio) { this.idLibroCambio = idLibroCambio; }
    public String getTituloLibroCambio() { return tituloLibroCambio; }
    public void setTituloLibroCambio(String tituloLibroCambio) { this.tituloLibroCambio = tituloLibroCambio; }
    public String getEstadoInter() { return estadoInter; }
    public void setEstadoInter(String estadoInter) { this.estadoInter = estadoInter; }
    public LocalDateTime getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(LocalDateTime fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }
    public LocalDateTime getFechaConfirmacion() { return fechaConfirmacion; }
    public void setFechaConfirmacion(LocalDateTime fechaConfirmacion) { this.fechaConfirmacion = fechaConfirmacion; }
}
