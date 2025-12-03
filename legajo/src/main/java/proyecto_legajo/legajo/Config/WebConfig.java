package proyecto_legajo.legajo.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/inventario_admi.html").setViewName("inventario_admi");
        registry.addViewController("/inventario.html").setViewName("inventario");
        registry.addViewController("/dashboard_admin.html").setViewName("dashboard_admin");
        registry.addViewController("/dashboard_usuario.html").setViewName("dashboard_usuario");
        registry.addViewController("/crear_cuenta.html").setViewName("crear_cuenta");
        registry.addViewController("/login.html").setViewName("login");
        registry.addViewController("/perfil_admin.html").setViewName("perfil_admin");
        registry.addViewController("/perfil.html").setViewName("perfil");
        registry.addViewController("/notificaciones.html").setViewName("notificaciones");
        registry.addViewController("/novedades_usuarios.html").setViewName("novedades_usuarios");
        registry.addViewController("/registrar_libro.html").setViewName("registrar_libro");
        registry.addViewController("/reporte_libros.html").setViewName("reporte_libros");
        registry.addViewController("/chats.html").setViewName("chats");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/404.html").setViewName("404");

    }
}

