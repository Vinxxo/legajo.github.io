package proyecto_legajo.legajo.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Mapear rutas públicas a las plantillas en src/main/resources/templates
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/login.html").setViewName("login");
        registry.addViewController("/crear_cuenta.html").setViewName("crear_cuenta");
        registry.addViewController("/dashboard_usuario.html").setViewName("dashboard_usuario");
        registry.addViewController("/dashboard_admin.html").setViewName("dashboard_admin");
        registry.addViewController("/perfil.html").setViewName("perfil");
    }
}
