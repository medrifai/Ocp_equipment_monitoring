package net.enjoy.springboot.registrationlogin.service;

import net.enjoy.springboot.registrationlogin.dto.ContactDto;

public interface ContactMessageService {
    void saveMessage(ContactDto contactDto);
    void sendEmail(ContactDto contactDto);
}
