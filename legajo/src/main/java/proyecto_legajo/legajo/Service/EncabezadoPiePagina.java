package proyecto_legajo.legajo.Service;

import java.awt.Color;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class EncabezadoPiePagina extends PdfPageEventHelper {

    private Image logo;

    public EncabezadoPiePagina() {
        try {
    java.net.URL logoURL = getClass().getClassLoader().getResource("templates/reportes/logo_claro1.jpg");
    if (logoURL != null) {
        logo = Image.getInstance(logoURL);
        logo.scaleToFit(55, 55);
    }
    } catch (Exception e) {
    e.printStackTrace();
    }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {

        PdfContentByte cb = writer.getDirectContent();

       
        cb.setColorFill(new Color(0, 36, 61));
        float headerHeight = 70f; 
       
        
        float headerOffsetDown = -30f;
        float headerBottomY = document.top() + headerOffsetDown; 
        float headerWidth = document.right() - document.left();
        cb.rectangle(document.left(), headerBottomY, headerWidth, headerHeight);
        cb.fill();
        

        try {
            if (logo != null) {
                // Centrar verticalmente el logo dentro de la banda del encabezado
                float logoY = headerBottomY + (headerHeight - logo.getScaledHeight()) / 2f;
                logo.setAbsolutePosition(document.left() + 10, logoY);
                cb.addImage(logo);
            }
        } catch (Exception ignored) {}

        
        try {
            Font tituloFont = new Font(Font.HELVETICA, 20, Font.BOLD, Color.WHITE);
            Phrase titulo = new Phrase("REPORTE DE LIBROS PUBLICADOS", tituloFont);

            float headerMiddleY = headerBottomY + headerHeight / 2f;
            float pageCenterX = (document.left() + document.right()) / 2f;

            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, titulo, pageCenterX, headerMiddleY, 0);
        } catch (Exception ignored) {}

        // Fecha debajo del encabezado
        try {
            Font fechaFont = new Font(Font.HELVETICA, 10, Font.NORMAL, Color.WHITE);
            String fechaStr = "Fecha de creación: " + new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date());

            float fechaY = headerBottomY + headerHeight - 60f; // Ajusta si la quieres más arriba/abajo

            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, new Phrase(fechaStr, fechaFont),
                    (document.left() + document.right()) / 2f, fechaY, 0);
        } catch (Exception ignored) {}

        
        cb.setLineWidth(1.2f);
        cb.moveTo(document.left(), headerBottomY);
        cb.lineTo(document.right(), headerBottomY);
        cb.stroke();

        // Número de página
        ColumnText.showTextAligned(
                cb,
                Element.ALIGN_CENTER,
                new Phrase("Página " + writer.getPageNumber()),
                (document.right() - document.left()) / 2 + document.leftMargin(),
                document.bottom() - 15,
                0
        );
    }
}

