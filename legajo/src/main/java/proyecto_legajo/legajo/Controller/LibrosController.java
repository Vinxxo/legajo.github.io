package proyecto_legajo.legajo.controller;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import proyecto_legajo.legajo.Entity.libros;

@Controller
@RequestMapping("/libros")
public class LibrosController {

    private final ListCrudRepository librosRepository;

    public LibrosController(ListCrudRepository librosRepository) {
        this.librosRepository = librosRepository;
    }

    // LISTAR
    @GetMapping
    public String listarLibros(Model model) {
        model.addAttribute("libros", librosRepository.findAllById(Sort.by(Sort.Direction.ASC, "titulo")));
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
    public String editarLibro(@PathVariable Long id, Model model) throws Throwable {
        libros libro = (libros) librosRepository.findById(id)
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
    public String eliminarLibro(@PathVariable Long id) {
        librosRepository.deleteById(id);
        return "redirect:/libros";
    }
}
