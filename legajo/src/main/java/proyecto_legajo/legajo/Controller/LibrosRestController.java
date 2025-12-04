package proyecto_legajo.legajo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import proyecto_legajo.legajo.Service.LibrosService;
import proyecto_legajo.legajo.Dto.LibroResponseDTO;

@RestController
@RequestMapping("/api/libros")
@CrossOrigin("*")

public class LibrosRestController {

    private final LibrosService service;

    public LibrosRestController(LibrosService service) {
        this.service = service;
    }

    @GetMapping
    public List<LibroResponseDTO> listar(
        @RequestParam(required = false) String usuario,
        @RequestParam(required = false) String titulo,
        @RequestParam(required = false) String autor,
        @RequestParam(required = false) String genero,
        @RequestParam(required = false) String estado
    ) {
        return service.buscarPorFiltros(usuario, titulo, autor, genero, estado);
    }
}
