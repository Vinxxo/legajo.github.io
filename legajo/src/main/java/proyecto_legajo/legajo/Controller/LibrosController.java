package proyecto_legajo.legajo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import proyecto_legajo.legajo.Repository.LibrosRepository;
import proyecto_legajo.legajo.Service.PdfService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletResponse;
import proyecto_legajo.legajo.Entity.libros;

@Controller
@RequestMapping("/libros")
public class LibrosController {

    @Autowired

    private PdfService pdfService;

    private final LibrosRepository librosRepository;

    public LibrosController(LibrosRepository librosRepository) {
        this.librosRepository = librosRepository;
    }

    // LISTAR
    @GetMapping
    public String listarLibros(Model model) {
        model.addAttribute("libros", librosRepository.findAll(Sort.by(Sort.Direction.ASC, "TituloLib")));
        return "libros/index";
    }

    // FORMULARIO NUEVO
    @GetMapping("/nuevo")
    public String nuevoLibro(Model model) {
        model.addAttribute("libro", new libros());
        return "libros/registrar";
    }

    // GUARDAR
    @PostMapping
    public String guardarLibro(@ModelAttribute libros libro, RedirectAttributes redirectAttrs) {
        librosRepository.save(libro);
        redirectAttrs.addFlashAttribute("msg", "guardado");
        return "redirect:/libros";
    }

    // EDITAR
    @GetMapping("/editar/{id}")
    public String editarLibro(@PathVariable int id, Model model) throws Throwable {
        libros libro = librosRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));

        model.addAttribute("libro", libro);
        return "libros/editar";
    }

    // ACTUALIZAR
    @PostMapping("/actualizar")
    public String actualizarLibro(@ModelAttribute libros libro) {
        librosRepository.save(libro);
        return "redirect:/libros";
    }

    // ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminarLibro(@PathVariable int id) {
        librosRepository.deleteById(id);
        return "redirect:/libros";
    }

   @GetMapping("/reporte/pdf")
    public void descargarPdfLibros(
        @RequestParam(required = false) String usuario,
        @RequestParam(required = false) String titulo,
        @RequestParam(required = false) String autor,
        @RequestParam(required = false) String genero,
        @RequestParam(required = false) String estado,
        HttpServletResponse response
        ) {
    try {
        pdfService.generarReporteLibros(response, usuario, titulo, autor, genero, estado);
    } catch (Exception e) {
        throw new RuntimeException("Error generando PDF", e);
    }
}


}
