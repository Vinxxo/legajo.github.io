package proyecto_legajo.legajo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping({"/","/index.html","/index"})
    public String index() {
        return "index";
    }

    @GetMapping({"/login.html","/login"})
    public String login() {
        return "login";
    }

    @GetMapping({"/crear_cuenta"})
    public String crearCuenta() {
        return "crear_cuenta";
    }

}
