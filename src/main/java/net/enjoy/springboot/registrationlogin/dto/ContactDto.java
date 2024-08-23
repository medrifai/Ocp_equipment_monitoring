package net.enjoy.springboot.registrationlogin.dto;

public class ContactDto {

    private String name;
    private String email;
    private String message;

    // Constructeurs
    public ContactDto() {
    }

    public ContactDto(String name, String email, String message) {
        this.name = name;
        this.email = email;
        this.message = message;
    }

    // Getters et Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}