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
    

    public void generarReporteLibros(HttpServletResponse response,
                                String usuario,
                                String titulo,
                                String autor,
                                String genero,
                                String estado) throws Exception {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=libros_reporte.pdf");

        // MÁRGENES AJUSTADOS PARA ENCABEZADO
        Document document = new Document(PageSize.A4.rotate(), 40, 40, 120, 50);
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());

        // Activar encabezado y pie de página
        writer.setPageEvent(new EncabezadoPiePagina());

        document.open();

        // ESPACIO PARA QUE NO SE MONTE CON EL ENCABEZADO
        document.add(new Paragraph("\n\n\n"));

        // TABLA DE 6 COLUMNAS
        PdfPTable tabla = new PdfPTable(5);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(20);

        tabla.setWidths(new float[]{2.5f, 3f, 3f, 2.5f, 1.8f });

        // ENCABEZADOS
        agregarCeldaEncabezado(tabla, "Usuario");
        agregarCeldaEncabezado(tabla, "Título");
        agregarCeldaEncabezado(tabla, "Autor(es)");
        agregarCeldaEncabezado(tabla, "Género(s)");
        agregarCeldaEncabezado(tabla, "Estado");

        // -------------------------------
        // LLENADO DE FILAS
        // -------------------------------
        List<libros> listaLibros = librosRepository.findAll();

        for (libros libro : listaLibros) {

            // USUARIO
            if (libro.getUsuarioPropietario() != null) {
                String propietario = libro.getUsuarioPropietario().getPrimerNombre()
                        + " " + libro.getUsuarioPropietario().getPrimerApellido();
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

    // -------------------------
    // MÉTODOS DE CELDAS
    // -------------------------

    private void agregarCeldaEncabezado(PdfPTable tabla, String texto) {
        Font font = new Font(Font.HELVETICA, 12, Font.BOLD, Color.BLACK);

        PdfPCell celda = new PdfPCell(new Paragraph(texto, font));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setBackgroundColor(new Color(210, 210, 210));
        celda.setPadding(7);
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
