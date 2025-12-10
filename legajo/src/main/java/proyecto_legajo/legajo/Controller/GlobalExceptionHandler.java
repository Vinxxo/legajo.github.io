package proyecto_legajo.legajo.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.mail.MailException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MailException.class)
    public ModelAndView handleMailException(MailException ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("error", "Error al enviar correo: " + ex.getMessage());
        return mav;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGenericException(Exception ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("error", "Error interno del servidor: " + ex.getMessage());
        return mav;
    }
}
