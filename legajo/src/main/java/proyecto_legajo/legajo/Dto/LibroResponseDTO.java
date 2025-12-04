package proyecto_legajo.legajo.Dto;

public class LibroResponseDTO {

    private String usuario;
    private String titulo;
    private String autor;
    private String genero;
    private String estado;

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
}
