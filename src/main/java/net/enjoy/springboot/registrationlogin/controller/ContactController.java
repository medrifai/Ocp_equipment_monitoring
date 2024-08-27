package net.enjoy.springboot.registrationlogin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import net.enjoy.springboot.registrationlogin.dto.ContactDto;
import net.enjoy.springboot.registrationlogin.service.ContactMessageService;

@Controller
public class ContactController {

    @Autowired
    private ContactMessageService contactMessageService;

    @GetMapping("/contact")
    public ModelAndView showContactForm(Model model) {
        addRoleAttributes(model); // Ajout des attributs d'authentification et de rôle au modèle
        ModelAndView mav = new ModelAndView("contact");
        mav.addObject("contactDto", new ContactDto());
        return mav;
    }

    @PostMapping("/contact")
    public ModelAndView submitContactForm(@ModelAttribute ContactDto contactDto, Model model) {
        addRoleAttributes(model); // Ajout des attributs d'authentification et de rôle au modèle
        contactMessageService.saveMessage(contactDto);
        contactMessageService.sendEmail(contactDto);

        ModelAndView mav = new ModelAndView("contact");
        mav.addObject("contactDto", new ContactDto()); // Réinitialise le DTO
        mav.addObject("successMessage", "Votre message a été envoyé avec succès.");
        return mav;
    }

    // Méthode privée pour ajouter l'état d'authentification et de rôle au modèle
    @SuppressWarnings("null")
    private void addRoleAttributes(Model model) {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);

        model.addAttribute("isAuthenticated", isAuthenticated);

        if (isAuthenticated) {
            model.addAttribute("isAdmin", authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN")));
            model.addAttribute("isTechnicien", authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_TECHNICIEN")));
        } else {
            model.addAttribute("isAdmin", false);
            model.addAttribute("isTechnicien", false);
        }
    }
}
