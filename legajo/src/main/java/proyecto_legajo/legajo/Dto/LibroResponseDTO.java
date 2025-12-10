package proyecto_legajo.legajo.Dto;

public class LibroResponseDTO {

    private int idLibro;
    private int usuarioPropietarioId;
    private String usuario;
    private String titulo;
    private String autor;
    private String genero;
    private String estado;
    private String urlImagen;
    private String descripcion;

    public int getIdLibro() { return idLibro; }
    public void setIdLibro(int idLibro) { this.idLibro = idLibro; }

    public int getUsuarioPropietarioId() { return usuarioPropietarioId; }
    public void setUsuarioPropietarioId(int usuarioPropietarioId) { this.usuarioPropietarioId = usuarioPropietarioId; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getUrlImagen() { return urlImagen; }
    public void setUrlImagen(String urlImagen) { this.urlImagen = urlImagen; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
