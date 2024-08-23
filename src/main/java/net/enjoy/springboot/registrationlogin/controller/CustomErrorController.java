package net.enjoy.springboot.registrationlogin.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        // Vous pouvez ajouter des logs ou d'autres traitements ici si nécessaire
        return "error/404"; // Assurez-vous que ce template existe
    }

    public String getErrorPath() {
        return "/error";
    }
}
