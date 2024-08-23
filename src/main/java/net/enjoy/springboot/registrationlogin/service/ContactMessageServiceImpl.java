package net.enjoy.springboot.registrationlogin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import net.enjoy.springboot.registrationlogin.dto.ContactDto;
import net.enjoy.springboot.registrationlogin.entity.ContactMessage;
import net.enjoy.springboot.registrationlogin.repository.ContactMessageRepository;

@Service
public class ContactMessageServiceImpl implements ContactMessageService {

    @Autowired
    private ContactMessageRepository contactMessageRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;


    @Override
    public void saveMessage(ContactDto contactDto) {
        ContactMessage contactMessage = new ContactMessage();
        contactMessage.setName(contactDto.getName());
        contactMessage.setEmail(contactDto.getEmail());
        contactMessage.setMessage(contactDto.getMessage());
        contactMessageRepository.save(contactMessage);
    }

    @Override
    public void sendEmail(ContactDto contactDto) {
        List<String> adminEmails = userService.getAdminEmails();
        
        // Log pour vérifier les adresses e-mail
        System.out.println("Admin emails: " + adminEmails);

        if (adminEmails.isEmpty()) {
            throw new IllegalStateException("Aucune adresse e-mail d'administrateur trouvée.");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(adminEmails.toArray(String[]::new));
        message.setSubject("Nouveau message de contact de " + contactDto.getName());
        message.setText("""
            Détails du message:

            Nom: %s
            Email: %s

            Message: 
            %s
        """.formatted(contactDto.getName(), contactDto.getEmail(), contactDto.getMessage()));

        mailSender.send(message);
    }
}
