package net.enjoy.springboot.registrationlogin.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;

public class EquipmentDto {

    private Long id;

    @NotEmpty(message = "Le nom de l'équipement ne doit pas être vide")
    private String name;

    @NotEmpty(message = "La localisation de l'équipement ne doit pas être vide")
    private String location;

    private String photoUrl;

    private MultipartFile photo;

    // Constructor without arguments
    public EquipmentDto() {}

    // Constructor with all arguments
    public EquipmentDto(Long id, String name, String location, String photoUrl, MultipartFile photo) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.photoUrl = photoUrl;
        this.photo = photo;
    }

    // Getters and Setters
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }
}
