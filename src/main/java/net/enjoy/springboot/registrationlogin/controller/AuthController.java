package net.enjoy.springboot.registrationlogin.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import net.enjoy.springboot.registrationlogin.dto.UserDto;
import net.enjoy.springboot.registrationlogin.entity.User;
import net.enjoy.springboot.registrationlogin.service.UserService;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Méthode pour afficher la page de connexion
    @GetMapping("/login")
    public String login(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);

        // Rediriger l'utilisateur authentifié directement vers la page des équipements
        if (isAuthenticated) {
            return "redirect:/equipments";
        }

        return "login";
    }

    // Méthode pour afficher la page d'inscription
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);

        if (isAuthenticated) {
            return "redirect:/equipments";
        }

        model.addAttribute("user", new UserDto());
        return "register";
    }

    // Méthode pour gérer la soumission du formulaire d'inscription
    @SuppressWarnings("null")
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null) {
            result.rejectValue("email", null, "There is already an account registered with the same email");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "register"; // Rediriger vers `register.html` en cas d'erreurs
        }

        userService.saveUser(userDto);
        return "redirect:/login?success";
    }

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        // Récupérer l'authentification de l'utilisateur
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Vérifier si l'utilisateur est authentifié
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);

        // Si l'utilisateur est authentifié, charger les informations de l'utilisateur (si nécessaire)
        // if (isAuthenticated) {
        //     model.addAttribute("username", authentication.getName()); // Par exemple, passer le nom d'utilisateur au modèle
        // }

        // Retourner la vue index.html sans redirection
        return "index";
    }

}
