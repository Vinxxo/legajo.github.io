package proyecto_legajo.legajo.Service;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import jakarta.servlet.http.HttpServletResponse;
import proyecto_legajo.legajo.Entity.libros;
import proyecto_legajo.legajo.Repository.LibrosRepository;

@Service
public class PdfService {

    @Autowired
    private LibrosRepository librosRepository;

    public void generarReporteLibros(HttpServletResponse response) throws Exception {

    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=libros_reporte.pdf");

    Document document = new Document(PageSize.A4, 40, 40, 70, 50);
    PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());

    // Activo encabezado y pie
    writer.setPageEvent(new EncabezadoPiePagina());

    document.open();

    // El título principal se renderiza en el encabezado (EncabezadoPiePagina)

    // Consulta BD
    List<libros> listaLibros = librosRepository.findAll();

    // Añadir espacios vacíos para separar del encabezado dibujado
    for (int i = 0; i < 3; i++) {
        document.add(new Paragraph(" "));
    }

    PdfPTable tabla = new PdfPTable(5);
    tabla.setWidthPercentage(100);
    tabla.setWidths(new float[]{1.8f, 1.8f, 1.8f, 1.8f, 1.8f});

    // Encabezados
    agregarCeldaEncabezado(tabla, "Usuario");
    agregarCeldaEncabezado(tabla, "Título");
    agregarCeldaEncabezado(tabla, "Autor");
    agregarCeldaEncabezado(tabla, "Género");
    agregarCeldaEncabezado(tabla, "Estado");

        // FILAS
        for (libros libro : listaLibros) {

            // USUARIO
            if (libro.getUsuarioPropietario() != null) {
                String propietario = libro.getUsuarioPropietario().getPrimerNombre() + " " +
                                     libro.getUsuarioPropietario().getPrimerApellido();
                agregarCeldaDatos(tabla, propietario);
            } else {
                agregarCeldaDatos(tabla, "Sin propietario");
            }

            // TÍTULO
            agregarCeldaDatos(tabla, libro.getTituloLib());

            // AUTORES
            String autores = libro.getAutor().stream()
                    .map(a -> a.getNomAutor1() + " " + a.getApeAutor1())
                    .collect(Collectors.joining(", "));
            agregarCeldaDatos(tabla, autores);

            // GÉNEROS
            String generos = libro.getGeneros().stream()
                    .map(g -> g.getGeneroLib())
                    .collect(Collectors.joining(", "));
            agregarCeldaDatos(tabla, generos);

            // ESTADO
            agregarCeldaDatos(tabla, libro.getEstadoLib().name());
        }

        document.add(tabla);
        document.close();
    }

    private void agregarCeldaEncabezado(PdfPTable tabla, String texto) {
        Font font = new Font(Font.HELVETICA, 12, Font.BOLD, Color.BLACK);

        PdfPCell celda = new PdfPCell(new Paragraph(texto, font));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setBackgroundColor(new Color(210, 210, 210));
        celda.setPadding(5);
        tabla.addCell(celda);
    }

    private void agregarCeldaDatos(PdfPTable tabla, String texto) {
        Font font = new Font(Font.HELVETICA, 11, Font.NORMAL, Color.BLACK);

        PdfPCell celda = new PdfPCell(new Paragraph(texto, font));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(5);
        tabla.addCell(celda);
    }
}
