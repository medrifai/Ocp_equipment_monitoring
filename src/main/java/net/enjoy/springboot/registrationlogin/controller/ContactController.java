package net.enjoy.springboot.registrationlogin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public ModelAndView showContactForm() {
        ModelAndView mav = new ModelAndView("contact");
        mav.addObject("contactDto", new ContactDto());
        return mav;
    }

    @PostMapping("/contact")
    public ModelAndView submitContactForm(@ModelAttribute ContactDto contactDto) {
        contactMessageService.saveMessage(contactDto);
        contactMessageService.sendEmail(contactDto);

        ModelAndView mav = new ModelAndView("contact");
        mav.addObject("contactDto", new ContactDto()); // Réinitialise le DTO
        mav.addObject("successMessage", "Votre message a été envoyé avec succès.");
        return mav;
    }
}